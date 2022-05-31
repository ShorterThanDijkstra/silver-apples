package backend.mwvb.service.impl;

import backend.mwvb.entity.LoginInfo;
import backend.mwvb.exception.UserLoginException;
import backend.mwvb.service.UserLoginService;
import backend.mwvb.util.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserLoginServiceImplTest {
    private UserLoginService userLoginService;

    @Autowired
    public void setUserService(UserLoginService userLoginService) {
        this.userLoginService = userLoginService;
    }

    @Test
    void login() throws UserLoginException {
        String email = "abcdd@gmail.com";
        String password = "Teswqwt127123";
        LoginInfo command = new LoginInfo(null, email, password);

        Response<String> response = userLoginService.login(command);
        System.out.println(response);

    }
}