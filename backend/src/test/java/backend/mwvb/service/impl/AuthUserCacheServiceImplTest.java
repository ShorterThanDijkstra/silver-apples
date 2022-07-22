package backend.mwvb.service.impl;

import backend.mwvb.entity.AuthUser;
import backend.mwvb.entity.User;
import backend.mwvb.service.AuthUserCacheService;
import backend.mwvb.service.UserRegisterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.mail.MessagingException;

import static backend.mwvb.test_util.UserTestUtil.registerRandomUser;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@TestPropertySource(locations = "classpath:test.properties")
@SpringBootTest
class AuthUserCacheServiceImplTest {
    @Autowired
    private AuthUserCacheService authUserCacheService;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private UserRegisterService userRegisterService;

    @Test
    void cacheAuthUser() throws MessagingException {
        User user = registerRandomUser(userRegisterService);
        AuthUser authUser = (AuthUser) userDetailService.loadUserByUsername(user.getEmail());
        Integer userId = authUser.getUser().getId();

        authUserCacheService.cacheAuthUserByUserId(authUser);
        AuthUser cachedAuthUser = authUserCacheService.getCachedAuthUser(String.valueOf(userId));
        assertThat(authUser, equalTo(cachedAuthUser));

    }

}