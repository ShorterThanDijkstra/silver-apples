package backend.mwvb.service.impl;

import backend.mwvb.entity.User;
import backend.mwvb.exception.IllegalUserInfoException;
import backend.mwvb.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceImplTest {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Test
    void register() throws IllegalUserInfoException {
        User user = User.builder()
                .email("dhlidj@qq.com")
                .name("test")
                .nickName("udedew")
                .password("12345")
                .build();
        userService.register(user);
    }
}