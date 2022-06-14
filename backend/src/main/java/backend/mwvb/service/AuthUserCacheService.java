package backend.mwvb.service;

import backend.mwvb.entity.AuthUser;

public interface AuthUserCacheService {
    String AUTH_USER_CACHE_PREFIX = "user:auth:id:";

    void cacheAuthUserByUserId(AuthUser authUser);

    boolean hasUser(String userId);

    AuthUser getCachedAuthUser(String userId);
}
