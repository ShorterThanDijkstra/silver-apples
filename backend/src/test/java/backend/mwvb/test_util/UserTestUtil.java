package backend.mwvb.test_util;

import backend.mwvb.entity.LoginInfo;
import backend.mwvb.entity.RegisterInfo;
import backend.mwvb.entity.User;
import backend.mwvb.service.UserLoginService;
import backend.mwvb.service.UserRegisterService;
import backend.mwvb.util.CommonJWTUtils;
import backend.mwvb.util.Response;
import lombok.val;
import org.apache.commons.lang3.RandomStringUtils;

import javax.mail.MessagingException;
import java.util.Map;

public class UserTestUtil {
    public static String randomEmail() {
        String emailPrefix = RandomStringUtils.randomAlphanumeric(5, 10);
        String emailSuffix = RandomStringUtils.randomAlphabetic(2, 5) +
                "." + RandomStringUtils.randomAlphabetic(2, 3);
        return emailPrefix + "@" + emailSuffix;
    }

    public static User randomUser() {
        String username = RandomStringUtils.randomAlphanumeric(5, 12);
        String password = RandomStringUtils.randomAlphanumeric(10, 15);
        String email = randomEmail();
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        return user;
    }

    public static RegisterInfo genRegisterInfo() {
        String username = RandomStringUtils.randomAlphanumeric(5, 12);
        String password = RandomStringUtils.randomAlphanumeric(10, 15);
        RegisterInfo info = new RegisterInfo();
        info.setUsername(username);
        info.setPassword(password);
        info.setConfirmedPassword(password);
        return info;
    }

    public static RegisterInfo use2registerInfo(User user, String token) {
        val registerInfo = new RegisterInfo();
        registerInfo.setToken(token);
        registerInfo.setUsername(user.getUsername());
        registerInfo.setPassword(user.getPassword());
        registerInfo.setConfirmedPassword(user.getPassword());
        return registerInfo;
    }

    public static LoginInfo user2loginInfo(User user) {
        val loginInfo = new LoginInfo();
        loginInfo.setEmail(user.getEmail());
        loginInfo.setPassword(user.getPassword());
        return loginInfo;
    }

    public static void register(UserRegisterService userRegisterService, User user) throws MessagingException {
        userRegisterService.request(user.getEmail());

        val token = CommonJWTUtils.lastToken();
        val info = use2registerInfo(user, token);

        userRegisterService.complete(info);
    }

    public static User registerRandomUser(UserRegisterService userRegisterService) throws MessagingException {
        User user = randomUser();
        register(userRegisterService, user);
        return user;
    }

    public static Map<String, String> login(UserLoginService userLoginService, User user) {
        LoginInfo info = user2loginInfo(user);
        Response<Map<String, String>> result = userLoginService.login(info);
        return result.getData();
    }
}
