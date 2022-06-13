package backend.mwvb.controller;

import backend.mwvb.entity.RegisterInfo;
import backend.mwvb.exception.UserRegisterException;
import backend.mwvb.service.UserLoginService;
import backend.mwvb.service.UserRegisterService;
import backend.mwvb.util.Response;
import lombok.Data;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.Email;

@RestController
@RequestMapping("/api/v1.0/user/register")
@Data
@CrossOrigin
@Validated
public class UserRegisterController {
    private final UserLoginService loginService;
    private final UserRegisterService registerService;

    @GetMapping("/request/{email}")
    public Response<String> request(@PathVariable @Valid @Email String email) throws UserRegisterException, MessagingException {
        registerService.request(email);
        return Response.success("请查收邮件，激活帐号");
    }

    @PostMapping("/complete")
    public Response<String> complete(@RequestBody RegisterInfo info) throws UserRegisterException {
        registerService.complete(info);
        return Response.success("创建用户成功");
    }

    @GetMapping("/email-exist/{email}")
    public Response<Boolean> emailExist(@PathVariable("email")  @Valid @Email String email) {
        boolean isUnique = registerService.emailExist(email);
        return Response.success(isUnique);
    }

    @GetMapping("/username-exist/{username}")
    public Response<Boolean> usernameExist(@PathVariable("username") String username) {
        boolean isUnique = registerService.usernameExist(username);
        return Response.success(isUnique);
    }
}
