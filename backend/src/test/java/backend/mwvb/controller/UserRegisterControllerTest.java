package backend.mwvb.controller;

import backend.mwvb.entity.RegisterInfo;
import backend.mwvb.entity.User;
import backend.mwvb.service.UserLoginService;
import backend.mwvb.service.UserRegisterService;
import backend.mwvb.test_util.UserTestUtil;
import backend.mwvb.util.CommonJWTUtils;
import lombok.val;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;

import javax.mail.MessagingException;
import java.util.Map;

import static backend.mwvb.test_util.RestAssuredTestUtil.*;
import static backend.mwvb.test_util.UserTestUtil.*;
import static org.hamcrest.Matchers.is;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserRegisterControllerTest {
    private static final String API = "http://localhost:8080/api/v1.0/user/register";
    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private UserLoginService userLoginService;

    RegisterInfo request() {
        User user = randomUser();
        val response = getWithoutTokenSuccessfully(API + "/request/" + user.getEmail());
        assertBody(response, "code", is(HttpStatus.OK.value()), "data", is("请于24小时内查收邮件，激活帐号"));

        String token = CommonJWTUtils.lastToken();
        return use2registerInfo(user, token);
    }

    @Test
    void register() {
        val registerInfo = request();
        val response = postWithoutTokenSuccessfully(API + "/complete", registerInfo);
        assertBody(response, "code", is(HttpStatus.OK.value()), "data", is("创建用户成功"));
    }

    @Test
    void registerError0() {
        RegisterInfo registerInfo = request();
        registerInfo.setConfirmedPassword(RandomStringUtils.randomAlphanumeric(10, 15));
        val response = postWithoutTokenSuccessfully(API + "/complete", registerInfo);
        assertBody(response, "code", is(HttpStatus.BAD_REQUEST.value()), "data", is("两次密码输入不一致"));
    }


    @Test
    void registerError1() {
        RegisterInfo registerInfo = request();
        registerInfo.setPassword(null);
        val response = postWithoutTokenSuccessfully(API + "/complete", registerInfo);
        assertBody(response, "code", is(HttpStatus.BAD_REQUEST.value()), "data", is("用户信息填写不全"));
    }

    @Test
    void registerError2() {
        RegisterInfo registerInfo = request();
        registerInfo.setUsername(RandomStringUtils.randomAlphanumeric(3));
        val response = postWithoutTokenSuccessfully(API + "/complete", registerInfo);
        assertBody(response,"code", is(HttpStatus.BAD_REQUEST.value()), "data", is("用户名长度应至少为5"));
    }

    @Test
    void registerError3() {
        RegisterInfo registerInfo = request();
        registerInfo.setPassword(RandomStringUtils.randomAlphanumeric(8));

        val response = postWithoutTokenSuccessfully(API + "/complete", registerInfo);
        assertBody(response,"code", is(HttpStatus.BAD_REQUEST.value()), "data", is("密码长度应至少为10"));

    }

    @Test
    void emailExist() throws MessagingException {
        User user = registerRandomUser(userRegisterService);
        val response0 = getWithoutTokenSuccessfully(API + "/email-exist/" + user.getEmail());
        assertBody(response0, "data", is(true));

        val response1 = getWithoutTokenSuccessfully(API + "/email-exist/" + randomEmail());
        assertBody(response1, "data", is(false));
    }

    @Test
    void usernameExist() throws MessagingException {
        User user = registerRandomUser(userRegisterService);
        val response0 = getWithoutTokenSuccessfully(API + "/username-exist/" + user.getUsername());
        assertBody(response0, "data", is(true));

        val response1 = getWithoutTokenSuccessfully(API + "/username-exist/" + RandomStringUtils.randomAlphanumeric(5));
        assertBody(response1, "data", is(false));
    }

    private String getLoginToken() throws MessagingException {
        User user = registerRandomUser(userRegisterService);
        Map<String, String> login = UserTestUtil.login(userLoginService, user);
        return login.get("token");
    }

    @Test
    void requestWithLoginToken() throws MessagingException {
        String loginToken = getLoginToken();
        val response = getWithTokenSuccessfully(API + "/request/" + randomEmail(), loginToken);
        assertBody(response, "code", is(HttpStatus.OK.value()), "data", is("请于24小时内查收邮件，激活帐号"));

    }

    @Test
    void registerWithLoginToken() throws MessagingException {
        String loginToken = getLoginToken();
        User user = randomUser();
        request();

        String token = CommonJWTUtils.lastToken();
        RegisterInfo registerInfo = use2registerInfo(user, token);
        val response = postWithTokenSuccessfully(API + "/complete", loginToken, registerInfo);
        assertBody(response, "code", is(HttpStatus.OK.value()), "data", is("创建用户成功"));

    }

}