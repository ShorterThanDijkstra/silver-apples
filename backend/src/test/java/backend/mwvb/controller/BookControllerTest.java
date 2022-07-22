package backend.mwvb.controller;

import backend.mwvb.entity.User;
import backend.mwvb.service.UserLoginService;
import backend.mwvb.service.UserRegisterService;
import backend.mwvb.util.CommonJWTUtils;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.mail.MessagingException;

import static backend.mwvb.test_util.RestAssuredTestUtil.*;
import static backend.mwvb.test_util.UserTestUtil.login;
import static backend.mwvb.test_util.UserTestUtil.registerRandomUser;
import static org.hamcrest.Matchers.*;

@TestPropertySource(locations = "classpath:test.properties")
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
        val response = getWithTokenSuccessfully(API + "/intro", token);
        assertBody(response, "data.paragraphs", hasSize(14));
    }

    @Test
    void allRoots() {
        val response = getWithTokenSuccessfully(API + "/roots", token);
        assertBody(response, "data.flatten().", hasSize(240));
    }

    @Test
    void rootsInUnit() {
        val response = getWithTokenSuccessfully(API + "/roots/17", token);
        assertBody(response, "data[5].name", equalTo("TANG/TACT"), "data", hasSize(8));
    }

    @Test
    void quizzesInUnit() {
        val response = getWithTokenSuccessfully(API + "/quizzes/27", token);
        assertBody(response, "data", hasSize(6), "data[1].quizPages[1].content", containsString("7. slander"));
    }

    @Test
    void wordsInRoot() {
        val response = getWithToken(API + "/roots/2", token);
        int rootId = response.body().jsonPath().get("data[5].id");

        val success = getWithTokenSuccessfully(API + "/words/" + rootId, token);
        assertBody(success, "data[2].spell", equalTo("protracted"));
    }

    @Test
    void allQuizzes() {
        val response = getWithTokenSuccessfully(API + "/quizzes", token);
        assertBody(response, "data.flatten()", hasSize(180));
    }

    @Test
    void unit() {
        val response = getWithTokenSuccessfully(API + "/units/7", token);
        assertBody(response, "data.specialSectionWords[2].sentences[0].text", containsString("Cassandra"));
    }

    @Test
    void unitWithoutToken() {
        getWithoutTokenForbidden(API + "/units/7");
    }


    @Test
    void quizzesInUnitWithBadToken() {

        String badToken = CommonJWTUtils.create("bad token", jwtKey);
        getWithBadToken(API + "/quizzes/10", badToken);
    }
}