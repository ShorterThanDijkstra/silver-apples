package backend.mwvb.service;

import backend.mwvb.entity.AuthUser;

public interface AuthUserCacheService {
    String AUTH_USER_CACHE_PREFIX = "user:auth:id:";
    void cacheAuthUser(AuthUser authUser);

    AuthUser getCachedAuthUser(String userId);
}
