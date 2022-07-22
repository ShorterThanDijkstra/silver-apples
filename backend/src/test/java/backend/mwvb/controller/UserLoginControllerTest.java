package backend.mwvb.controller;

import backend.mwvb.entity.LoginInfo;
import backend.mwvb.entity.User;
import backend.mwvb.service.UserLoginService;
import backend.mwvb.service.UserRegisterService;
import backend.mwvb.test_util.UserTestUtil;
import backend.mwvb.util.CommonJWTUtils;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import javax.mail.MessagingException;
import java.util.Map;

import static backend.mwvb.test_util.RestAssuredTestUtil.*;
import static backend.mwvb.test_util.UserTestUtil.registerRandomUser;
import static backend.mwvb.test_util.UserTestUtil.user2loginInfo;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

@TestPropertySource(locations = "classpath:test.properties")
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

        val response = postWithoutTokenSuccessfully(API + "/user/login", info);
        String token = CommonJWTUtils.lastToken();

        assertBody(response, "data.token", equalTo(token));
        assertBody(response, "data.email", equalTo(user.getEmail()));
        assertBody(response, "data.username", equalTo(user.getUsername()));

        getWithTokenSuccessfully(API + "/book/quizzes/13", token);
    }

    @Test
    void notLogin() {
        getWithoutTokenForbidden(API + "/book/quizzes/13");
    }


    @Test
    void loginWithToken() throws MessagingException {
        User user = registerRandomUser(userRegisterService);
        Map<String, String> login = UserTestUtil.login(userLoginService, user);
        String token = login.get("token");
        val loginInfo = user2loginInfo(user);
        val response = postWithTokenSuccessfully(API + "/user/login", token, loginInfo);

        assertBody(response, "code", equalTo(HttpStatus.OK.value()), "data.token", not(equalTo(token)));
    }
}