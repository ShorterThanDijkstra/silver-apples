package backend.mwvb.util;

import backend.mwvb.service.UserRegisterService;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@SpringBootTest
class RegisterRedisCacheTest {
    @Autowired
    private RegisterRedisCache registerRedisCache;
    @Value("${com.maerd_zinbiel.silver-apples.jwt.password}")
    private String jwtKey;

    @Test
    void jwt() {
        String token = CommonJWTUtils.create("test", jwtKey, UserRegisterService.REGISTER_INFO_EXPIRE);
        Claims claims = CommonJWTUtils.parse(token, jwtKey);
        assertThat(claims.getSubject(), is("test"));
    }

}