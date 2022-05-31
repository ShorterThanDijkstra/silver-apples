package backend.mwvb.service.impl;

import backend.mwvb.entity.User;
import backend.mwvb.exception.UserRegisterException;
import backend.mwvb.service.UserRegisterService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRegisterServiceImplTest {
    private UserRegisterService registerService;

    @Autowired
    public void setRegisterService(UserRegisterService registerService) {
        this.registerService = registerService;
    }

    @Test
    @Disabled
    void register() throws UserRegisterException {
        User user = User.builder()
                .email("dhdddlidj@qq.com")
                .name("tesddt")
                .nickName("udedew")
                .password("123123451234545")
                .confirmPassword("123123451234545")
                .build();
        registerService.register(user);
    }
}