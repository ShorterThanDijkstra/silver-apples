package backend.mwvb.service.impl;

import backend.mwvb.entity.User;
import backend.mwvb.exception.UserLoginException;
import backend.mwvb.mapper.UserMapper;
import backend.mwvb.service.UserLoginService;
import backend.mwvb.service.UserRegisterService;
import backend.mwvb.util.CommonJWTUtils;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;

import static backend.mwvb.test_util.UserTestUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class UserLoginServiceImplTest {
    @Autowired
    private UserLoginService userLoginService;

    @Autowired
    private UserRegisterService userRegisterService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${com.maerd_zinbiel.silver-apples.jwt.password}")
    private String jwtKey;


    @Test
    void login() throws UserLoginException, MessagingException {
        User randomUser = randomUser();
        register(userRegisterService, randomUser);
        val result = userLoginService.login(user2loginInfo(randomUser));
        val userFromDB = userMapper.queryUserByEmail(randomUser.getEmail());
        val token = result.getData().get("token");
        val claims = CommonJWTUtils.parse(token, jwtKey);
        val subject = claims.getSubject();
        val userId = claims.get(UserLoginService.LOGIN_JWT_CLAIMS_KEY, String.class);

        assertThat(randomUser.getEmail(), equalTo(userFromDB.getEmail()));
        assertThat(randomUser.getUsername(), equalTo(userFromDB.getUsername()));
        assertThat(passwordEncoder.matches(randomUser.getPassword(), userFromDB.getPassword()), is(true));

        assertThat(subject, equalTo(UserLoginService.LOGIN_JWT_SUBJECT));
        assertThat(Integer.valueOf(userId), equalTo(userFromDB.getId()));

    }

    @Test
    void loginWithBadToken() {
        val token = CommonJWTUtils.create("no-meaning", jwtKey);


    }
}