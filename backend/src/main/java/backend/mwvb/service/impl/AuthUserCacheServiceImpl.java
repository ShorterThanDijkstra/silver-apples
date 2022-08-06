package backend.mwvb.service.impl;

import backend.mwvb.entity.AuthUser;
import backend.mwvb.exception.UserAuthenticationException;
import backend.mwvb.service.AuthUserCacheService;
import lombok.Data;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Data
public class AuthUserCacheServiceImpl implements AuthUserCacheService {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void cacheAuthUserByUserId(AuthUser authUser) {
        Integer userId = authUser.getUser().getId();
        String key = genKey(String.valueOf(userId));
        redisTemplate.opsForValue().set(key, authUser);
    }

    @Override
    public boolean hasUser(String userId) {
        String key = genKey(userId);
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    private String genKey(String userId) {
        return AUTH_USER_CACHE_PREFIX + userId;
    }

    @Override
    public AuthUser getCachedAuthUser(String userId) {
        String key = genKey(userId);
        Object result = redisTemplate.opsForValue().get(key);
        if (result == null) {
            throw new UserAuthenticationException("invalid user id");
        }
        return (AuthUser) Objects.requireNonNull(result);
    }
}
