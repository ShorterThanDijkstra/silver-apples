package backend.mwvb.service.impl;

import backend.mwvb.entity.RegisterInfo;
import backend.mwvb.entity.User;
import backend.mwvb.exception.UserRegisterException;
import backend.mwvb.service.UserRegisterService;
import backend.mwvb.util.CommonJWTUtils;
import backend.mwvb.util.RegisterRedisCache;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.mail.MessagingException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class UserRegisterServiceImplTest {
    @Autowired
    private UserRegisterService registerService;

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Value("${com.maerd_zinbiel.silver-apples.jwt.password}")
    private String jwtKey;

    @Autowired
    private RegisterRedisCache registerRedisCache;

    @Test
    @Disabled
    void register() throws UserRegisterException {
        String name = "name";
        String email = "email@gmail.com";
        String password = "thisisapassword";
        String confirmedPassword = "thisisapassword";
        RegisterInfo info = new RegisterInfo(name, email, password, confirmedPassword);
        registerService.register(info);
    }

    private RegisterInfo GenRegisterInfo() {
        String name = RandomStringUtils.randomAlphanumeric(5, 10);
        String password = RandomStringUtils.randomAlphanumeric(10, 16);
        String email = "1544928966@qq.com";
        return new RegisterInfo(
                name,
                email,
                password,
                password
        );
    }

    @Test
    void request() throws UserRegisterException, MessagingException {
        RegisterInfo info = GenRegisterInfo();
        registerService.request(info);

        String jwtToken = CommonJWTUtils.lastToken();

        User user = registerRedisCache.getRegisterRequestUser(jwtToken);
        assertThat(info.username(), equalTo(user.getName()));
        assertThat(info.email(), equalTo(user.getEmail()));
        assertThat(passwordEncoder.matches(info.password(), user.getPassword()), is(true));

        Claims claims = CommonJWTUtils.parse(jwtToken, jwtKey);
        assertThat(claims.getSubject(), equalTo(info.email()));
    }
}