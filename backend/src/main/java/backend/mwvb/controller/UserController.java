package backend.mwvb.controller;

import backend.mwvb.entity.User;
import backend.mwvb.exception.IllegalUserInfoException;
import backend.mwvb.service.UserService;
import backend.mwvb.util.Response;
import lombok.Data;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1.0/user")
@Data
public class UserController {
    private final UserService userService;
    @PostMapping("/register")
    public Response<String> register(@RequestBody User user) throws IllegalUserInfoException {
        userService.register(user);
        return Response.success("创建用户成功");
    }
}
