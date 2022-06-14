package backend.mwvb.controller;

import backend.mwvb.entity.RegisterInfo;
import backend.mwvb.entity.User;
import backend.mwvb.service.UserLoginService;
import backend.mwvb.service.UserRegisterService;
import backend.mwvb.test_util.UserTestUtil;
import backend.mwvb.util.CommonJWTUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import javax.mail.MessagingException;
import java.util.Map;

import static backend.mwvb.config.SecurityConfig.TOKEN_HEADER_NAME;
import static backend.mwvb.test_util.UserTestUtil.*;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserRegisterControllerTest {
    private static final String API = "http://localhost:8080/api/v1.0/user/register";
    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private UserLoginService userLoginService;

    RegisterInfo request() {
        User user = randomUser();

        RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                when().
                get(API + "/request/" + user.getEmail())
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("code", is(HttpStatus.OK.value()))
                .body("data", is("请查收邮件，激活帐号"));


        String token = CommonJWTUtils.lastToken();
        return use2registerInfo(user, token);
    }

    @Test
    void register() {
        User user = randomUser();

        request();

        String token = CommonJWTUtils.lastToken();
        RegisterInfo registerInfo = use2registerInfo(user, token);

        RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(registerInfo).
                when().
                post(API + "/complete")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("code", is(HttpStatus.OK.value()))
                .body("data", is("创建用户成功"));
    }

    @Test
    void registerError0() {
        RegisterInfo registerInfo = request();
        registerInfo.setConfirmedPassword(RandomStringUtils.randomAlphanumeric(10, 15));
        RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(registerInfo).
                when().
                post(API + "/complete")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("code", is(400))
                .body("data", is("两次密码输入不一致"));
    }


    @Test
    void registerError1() {
        RegisterInfo registerInfo = request();
        registerInfo.setPassword(null);

        RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(registerInfo).
                when().
                post(API + "/complete")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("code", is(400))
                .body("data", is("用户信息填写不全"));
    }

    @Test
    void registerError2() {
        RegisterInfo registerInfo = request();
        registerInfo.setUsername(RandomStringUtils.randomAlphanumeric(3));

        RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(registerInfo).
                when().
                post(API + "/complete")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("code", is(400))
                .body("data", is("用户名长度至少为5"));
    }

    @Test
    void registerError3() {
        RegisterInfo registerInfo = request();
        registerInfo.setPassword(RandomStringUtils.randomAlphanumeric(8));

        RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(registerInfo).
                when().
                post(API + "/complete")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("code", is(400))
                .body("data", is("密码长度至少为10"));
    }

    @Test
    void emailExist() throws MessagingException {
        User user = registerRandomUser(userRegisterService);
        RestAssured.given()
                .get(API + "/email-exist/" + user.getEmail())
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data", is(true));

        RestAssured.given()
                .get(API + "/email-exist/" + randomEmail())
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data", is(false));
    }

    @Test
    void usernameExist() throws MessagingException {
        User user = registerRandomUser(userRegisterService);
        RestAssured.given()
                .get(API + "/username-exist/" + user.getUsername())
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data", is(true));

        RestAssured.given()
                .get(API + "/username-exist/" + RandomStringUtils.randomAlphanumeric(5))
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data", is(false));
    }

    private String getLoginToken() throws MessagingException {
        User user = registerRandomUser(userRegisterService);
        Map<String, String> login = UserTestUtil.login(userLoginService, user);
        return login.get("token");
    }

    @Test
    void requestWithLoginToken() throws MessagingException {
        String loginToken = getLoginToken();
        RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                header(TOKEN_HEADER_NAME, loginToken)
                .when().
                get(API + "/request/" + randomEmail())
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("code", is(HttpStatus.OK.value()))
                .body("data", is("请查收邮件，激活帐号"));

    }

    @Test
    void registerWithLoginToken() throws MessagingException {
        String loginToken = getLoginToken();

        User user = randomUser();

        request();

        String token = CommonJWTUtils.lastToken();
        RegisterInfo registerInfo = use2registerInfo(user, token);
        RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                header(TOKEN_HEADER_NAME, loginToken).
                body(registerInfo).
                when().
                post(API + "/complete")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("code", is(HttpStatus.OK.value()))
                .body("data", is("创建用户成功"));
    }

}