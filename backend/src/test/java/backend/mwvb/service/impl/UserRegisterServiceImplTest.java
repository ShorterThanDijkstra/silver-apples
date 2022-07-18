package backend.mwvb.service.impl;

import backend.mwvb.entity.RegisterInfo;
import backend.mwvb.entity.User;
import backend.mwvb.exception.UserRegisterException;
import backend.mwvb.mapper.UserMapper;
import backend.mwvb.service.UserLoginService;
import backend.mwvb.service.UserRegisterService;
import backend.mwvb.test_util.UserTestUtil;
import backend.mwvb.util.CommonJWTUtils;
import io.jsonwebtoken.Claims;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;

import static backend.mwvb.service.UserLoginService.LOGIN_JWT_CLAIMS_KEY;
import static backend.mwvb.service.UserLoginService.LOGIN_JWT_SUBJECT;
import static backend.mwvb.service.UserRegisterService.*;
import static backend.mwvb.test_util.UserTestUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserRegisterServiceImplTest {
    @Autowired
    private UserRegisterService registerService;
    @Value("${com.maerd_zinbiel.silver-apples.jwt.password}")
    private String jwtKey;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserLoginService loginService;

    @Test
    void request() throws UserRegisterException, MessagingException {
        String email = randomEmail();

        registerService.request(email);

        String jwtToken = CommonJWTUtils.lastToken();

        Claims claims = CommonJWTUtils.parse(jwtToken, jwtKey);
        assertThat(claims.getSubject(), equalTo(REGISTER_JWT_SUBJECT));

        assertThat(claims.get(REGISTER_JWT_CLAIMS_KEY, String.class), equalTo(email));
    }

    @Test
    void requestError() throws MessagingException {
        String email = randomEmail();
        registerService.request(email);

        RegisterInfo info = genRegisterInfo();
        String jwtToken = CommonJWTUtils.lastToken();
        info.setToken(jwtToken);

        registerService.complete(info);

        assertThrows(UserRegisterException.class, () -> registerService.complete(info), "此邮箱已经被注册");
    }


    @Test
    void register() throws MessagingException {
        String email = randomEmail();
        registerService.request(email);

        RegisterInfo info = genRegisterInfo();
        String jwtToken = CommonJWTUtils.lastToken();
        info.setToken(jwtToken);

        registerService.complete(info);
        User user = userMapper.queryUserByEmail(email);

        assertThat(user.getEmail(), equalTo(email));
        assertThat(user.getUsername(), equalTo(info.getUsername()));
        assertThat(passwordEncoder.matches(info.getPassword(), user.getPassword()), is(true));

    }

    @Test
    void registerError0() throws MessagingException {
        String email = randomEmail();
        registerService.request(email);

        RegisterInfo info = genRegisterInfo();
        String jwtToken = CommonJWTUtils.create(email, "random", REGISTER_INFO_EXPIRE);
        info.setToken(jwtToken);
        assertThrows(UserRegisterException.class, () -> registerService.complete(info));

    }

    @Test
    void registerWithOtherToken() throws MessagingException {
        val user = randomUser();
        UserTestUtil.register(registerService, user);

        val response = loginService.login(user2loginInfo(user));
        val otherToken = response.getData()
                .get("token");

        val registerInfo = genRegisterInfo();
        registerInfo.setToken(otherToken);
        assertThrows(UserRegisterException.class, () -> registerService.complete(registerInfo));
    }
}