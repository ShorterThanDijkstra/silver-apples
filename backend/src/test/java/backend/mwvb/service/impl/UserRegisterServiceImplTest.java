package backend.mwvb.service.impl;

import backend.mwvb.entity.RegisterInfo;
import backend.mwvb.exception.UserRegisterException;
import backend.mwvb.service.UserRegisterService;
import backend.mwvb.util.CommonJWTUtils;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.MessagingException;

import static backend.mwvb.service.UserRegisterService.REGISTER_INFO_EXPIRE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class UserRegisterServiceImplTest {
    @Autowired
    private UserRegisterService registerService;
    @Value("${com.maerd_zinbiel.silver-apples.jwt.password}")
    private String jwtKey;

    @Test
    @Disabled
    void request() throws UserRegisterException, MessagingException {
        String email = "1544928966@qq.com";

        registerService.request(email);

        String jwtToken = CommonJWTUtils.lastToken();

        Claims claims = CommonJWTUtils.parse(jwtToken, jwtKey);
        assertThat(claims.getSubject(), equalTo(email));
    }

    @Test
    void requestError() throws MessagingException {
        String email = randomEmail();
        registerService.request(email);
        assertThrows(UserRegisterException.class, () -> registerService.request(email), "此邮箱已经被注册");
    }

    private String randomEmail() {
        String emailPrefix = RandomStringUtils.randomAlphanumeric(5, 10);
        String emailSuffix = RandomStringUtils.randomAlphabetic(2, 5) +
                "." + RandomStringUtils.randomAlphabetic(2, 3);
        return emailPrefix + "@" + emailSuffix;
    }

    private RegisterInfo genRegisterInfo() {
        String username = RandomStringUtils.randomAlphanumeric(5, 12);
        String password = RandomStringUtils.randomAlphanumeric(10, 15);
        RegisterInfo info = new RegisterInfo();
        info.setUsername(username);
        info.setPassword(password);
        info.setConfirmedPassword(password);
        return info;
    }

    @Test
    void register() throws MessagingException {
        String email = randomEmail();
        registerService.request(email);

        RegisterInfo info = genRegisterInfo();
        String jwtToken = CommonJWTUtils.lastToken();
        info.setToken(jwtToken);

        registerService.complete(info);

        assertThat(info.getEmail(), equalTo(email));
        System.out.println(info);
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
    void jwtTest(){
        String email = "1544928966@qq.com";
        String jwtToken = CommonJWTUtils.create(email, jwtKey, REGISTER_INFO_EXPIRE);
        System.out.println(jwtToken);

        String hackedToken = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJlMGRlNjViNi1jZWZhLTRjMDctYjI2Mi0wYjE0N2FmNTVmM2IiLCJzdWIiOiIxNTQ0OTI4OTQ0ODhAcXEuY29tIiwiaWF0IjoxNjU0NTEzMzA5LCJleHAiOjE2NTQ1OTk3MDl9.3s3zvL5RkqRrQECv4mWCDUypz_6t3T4MFxMTYZNmXEk";
        Claims claims = CommonJWTUtils.parse(hackedToken, jwtKey);
        System.out.println(claims.getSubject());
    }
}