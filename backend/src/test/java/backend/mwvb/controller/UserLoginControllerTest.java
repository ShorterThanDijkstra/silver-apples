package backend.mwvb.controller;

import backend.mwvb.entity.LoginInfo;
import backend.mwvb.entity.User;
import backend.mwvb.service.UserRegisterService;
import backend.mwvb.util.CommonJWTUtils;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import javax.mail.MessagingException;

import static backend.mwvb.test_util.UserTestUtil.registerRandomUser;
import static backend.mwvb.test_util.UserTestUtil.user2loginInfo;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserLoginControllerTest {
    private static final String API = "http://localhost:8080/api/v1.0";

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
                .header("token", token)
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
}