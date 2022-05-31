package backend.mwvb.controller;

import backend.mwvb.entity.LoginInfo;
import backend.mwvb.entity.User;
import backend.mwvb.exception.UserRegisterException;
import backend.mwvb.exception.UserLoginException;
import backend.mwvb.service.UserRegisterService;
import backend.mwvb.service.UserLoginService;
import backend.mwvb.util.Response;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/user")
@Data
@CrossOrigin
public class UserController {
    private final UserLoginService loginService;
    private final UserRegisterService registerService;

    @PostMapping("/register")
    public Response<String> register(@Valid @RequestBody User user) throws UserRegisterException {
        registerService.register(user);
        return Response.success("创建用户成功");
    }

    @PostMapping("/login")
    public Response<String> login(@RequestBody LoginInfo loginInfo) throws UserLoginException {
        return loginService.login(loginInfo);
    }

    @GetMapping("/register/email/{email}")
    public Response<Boolean> emailExist(@PathVariable("email") String email) {
        boolean isUnique = registerService.emailExist(email);
        return Response.success(isUnique);
    }

    @GetMapping("/register/username/{username}")
    public Response<Boolean> usernameExist(@PathVariable("username") String username) {
        boolean isUnique = registerService.usernameExist(username);
        return Response.success(isUnique);
    }
}
