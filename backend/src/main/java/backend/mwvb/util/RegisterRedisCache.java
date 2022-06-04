package backend.mwvb.util;

import backend.mwvb.entity.User;
import lombok.Data;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Data
@Component
public class RegisterRedisCache {
    private final StringRedisTemplate redisTemplate;
    public static final String CACHE_REGISTER_REQUEST_EMAIL_PREFIX = "user:register:request:";

    public void cacheRegisterRequestUser(String jwtToken, User user, Long expireMillis) {
        Map<String, String> userMap = Map.of(
                "username", user.getName(),
                "email", user.getEmail(),
                "password", user.getPassword()
        );

        HashOperations<String, Object, Object> userCache = redisTemplate.opsForHash();
        String key = genKey(jwtToken);

        boolean hasKey = Boolean.TRUE.equals(redisTemplate.hasKey(key));
        // TODO: 2022/6/5 如果key已经存在
        userCache.putAll(key, userMap);
        redisTemplate.expire(key, expireMillis, TimeUnit.MILLISECONDS);
    }

    private String genKey(String jwtToken) {
        // TODO: 2022/6/5 使用邮箱作为key
        String jwtPayload = jwtToken.split("\\.")[1];
        return CACHE_REGISTER_REQUEST_EMAIL_PREFIX + jwtPayload;
    }

    public User getRegisterRequestUser(String jwtToken) {
        String key = genKey(jwtToken);
        HashOperations<String, Object, Object> userCache = redisTemplate.opsForHash();
        String username = (String) userCache.get(key, "username");
        String email = (String) userCache.get(key, "email");
        String password = (String) userCache.get(key, "password");
        return User.builder()
                .name(username)
                .email(email)
                .password(password)
                .build();
    }
}