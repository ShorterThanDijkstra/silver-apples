package backend.mwvb.controller;

import backend.mwvb.entity.LoginInfo;
import backend.mwvb.exception.UserLoginException;
import backend.mwvb.service.UserLoginService;
import backend.mwvb.util.Response;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1.0/user")
@Data
@CrossOrigin
public class UserLoginController {
    private final UserLoginService loginService;

    @PostMapping("/login")
    public Response<String> login(@RequestBody LoginInfo loginInfo) throws UserLoginException {
        return loginService.login(loginInfo);
    }

}
