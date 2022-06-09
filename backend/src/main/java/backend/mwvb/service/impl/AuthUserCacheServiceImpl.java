package backend.mwvb.service.impl;

import backend.mwvb.entity.AuthUser;
import backend.mwvb.service.AuthUserCacheService;
import lombok.Data;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@Data
public class AuthUserCacheServiceImpl implements AuthUserCacheService {
    private final RedisTemplate<String, Object> redisTemplate;

    @Override
    public void cacheAuthUser(AuthUser authUser) {
        Integer userId = authUser.getUser().getId();
        String key = AUTH_USER_CACHE_PREFIX + userId;
        redisTemplate.opsForValue().set(key, authUser);
    }

    @Override
    public AuthUser getCachedAuthUser(String userId) {
        String key = AUTH_USER_CACHE_PREFIX + userId;
        return (AuthUser) redisTemplate.opsForValue().get(key);
    }
}
