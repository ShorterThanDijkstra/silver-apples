package backend.mwvb.controller;

import backend.mwvb.entity.User;
import backend.mwvb.exception.IllegalUserInfoException;
import backend.mwvb.service.UserService;
import backend.mwvb.util.Response;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1.0/user")
@Data
@CrossOrigin
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public Response<String> register(@Valid @RequestBody User user) throws IllegalUserInfoException {
        userService.register(user);
        return Response.success("创建用户成功");
    }

    @GetMapping("/register/email/{email}")
    public Response<Boolean> emailExist(@PathVariable("email") String email) {
        boolean isUnique = userService.emailExist(email);
        return Response.success(isUnique);
    }

    @GetMapping("/register/username/{username}")
    public Response<Boolean> usernameExist(@PathVariable("username") String username) {
        boolean isUnique = userService.usernameExist(username);
        return Response.success(isUnique);
    }
}
