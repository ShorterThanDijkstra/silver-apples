package backend.mwvb.controller;

import backend.mwvb.entity.User;
import backend.mwvb.service.UserLoginService;
import backend.mwvb.service.UserRegisterService;
import backend.mwvb.util.CommonJWTUtils;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import javax.mail.MessagingException;

import static backend.mwvb.config.SecurityConfig.TOKEN_HEADER_NAME;
import static backend.mwvb.test_util.UserTestUtil.login;
import static backend.mwvb.test_util.UserTestUtil.registerRandomUser;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class BookControllerTest {
    @Autowired
    private UserRegisterService userRegisterService;
    @Autowired
    private UserLoginService userLoginService;
    private static final String API = "http://localhost:8080/api/v1.0/book";
    private String token;

    @Value("${com.maerd_zinbiel.silver-apples.jwt.password}")
    private String jwtKey;

    @BeforeEach
    void setUp() throws MessagingException {
        User user = registerRandomUser(userRegisterService);
        token = login(userLoginService, user).get("token");
    }

    @Test
    void intro() {
        RestAssured.given()
                .header(TOKEN_HEADER_NAME, token)
                .get(API + "/intro")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data.paragraphs", hasSize(14));
    }

    @Test
    void allRoots() {
        RestAssured.given()
                .header(TOKEN_HEADER_NAME, token)
                .get(API + "/roots")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data.flatten().", hasSize(240));
    }

    @Test
    void rootsInUnit() {
        RestAssured.given()
                .header(TOKEN_HEADER_NAME, token)
                .get(API + "/roots/17")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data[5].name", equalTo("TANG/TACT"))
                .and()
                .body("data", hasSize(8));
    }

    @Test
    void quizzesInUnit() {
        RestAssured.given()
                .header(TOKEN_HEADER_NAME, token)
                .get(API + "/quizzes/27")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data", hasSize(6))
                .and()
                .body("data[1].quizPages[1].content", containsString("7. slander"));
    }

    @Test
    void wordsInRoot() {
        Response response = RestAssured.given()
                .header(TOKEN_HEADER_NAME, token)
                .get(API + "/roots/2");
        int rootId = response.body().jsonPath().get("data[5].id");
        RestAssured.given()
                .header(TOKEN_HEADER_NAME, token)
                .get(API + "/words/" + rootId)
                .then()
                .body("data[2].spell", equalTo("protracted"));
    }

    @Test
    void allQuizzes() {
        RestAssured.given()
                .header(TOKEN_HEADER_NAME, token)

                .get(API + "/quizzes")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .and()
                .body("data.flatten()", hasSize(180));

    }

    @Test
    void unit() {
        RestAssured.given()
                .header(TOKEN_HEADER_NAME, token)
                .get(API + "/units/7")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.OK.value()))
                .body("data.specialSectionWords[2].sentences[0].text", containsString("Cassandra"));
    }

    @Test
    void unitWithoutToken() {
        RestAssured.given()
                .get(API + "/units/7")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.FORBIDDEN.value()));
    }


    @Test
    void quizzesInUnitWithBadToken() {
        String badToken = CommonJWTUtils.create("bad token", jwtKey);
        RestAssured.given()
                .header(TOKEN_HEADER_NAME, badToken)
                .get(API + "/quizzes/10")
                .then()
                .assertThat()
                .statusCode(equalTo(HttpStatus.FORBIDDEN.value()));
    }
}