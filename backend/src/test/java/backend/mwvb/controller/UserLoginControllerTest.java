package backend.mwvb.controller;

import backend.mwvb.entity.LoginInfo;
import backend.mwvb.entity.User;
import backend.mwvb.service.UserLoginService;
import backend.mwvb.service.UserRegisterService;
import backend.mwvb.test_util.UserTestUtil;
import backend.mwvb.util.CommonJWTUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import javax.mail.MessagingException;
import java.util.Map;

import static backend.mwvb.config.SecurityConfig.TOKEN_HEADER_NAME;
import static backend.mwvb.test_util.UserTestUtil.registerRandomUser;
import static backend.mwvb.test_util.UserTestUtil.user2loginInfo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserLoginControllerTest {
    private static final String API = "http://localhost:8080/api/v1.0";

    @Autowired
    private UserLoginService userLoginService;
    @Autowired
    private UserRegisterService userRegisterService;

    @Test
    void login() throws MessagingException {
        User user = registerRandomUser(userRegisterService);
        LoginInfo info = user2loginInfo(user);
        Response response = RestAssured.given().
                accept(ContentType.JSON).
                contentType(ContentType.JSON).
                body(info).
                when().
                post(API + "/user/login");

        String token = CommonJWTUtils.lastToken();
        response.then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data.token", equalTo(token));

        RestAssured.given()
                .header(TOKEN_HEADER_NAME, token)
                .get(API + "/book/quizzes/13")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()));
    }

    @Test
    void notLogin() {
        RestAssured.given()
                .get(API + "/book/quizzes/13")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.FORBIDDEN.value()));
    }


    @Test
    void loginWithToken() throws MessagingException {
        User user = registerRandomUser(userRegisterService);
        Map<String, String> login = UserTestUtil.login(userLoginService, user);
        String token = login.get("token");

        Response response = RestAssured.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .header(TOKEN_HEADER_NAME, token)
                .body(user2loginInfo(user))
                .post(API + "/user/login");
        response.body().print();
        response
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("code", equalTo(HttpStatus.OK.value()))
                .body("data.token", not(equalTo(token)));
    }
}