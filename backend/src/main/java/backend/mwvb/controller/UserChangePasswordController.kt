package backend.mwvb.controller

import backend.mwvb.entity.ResetPasswordInfo
import backend.mwvb.exception.UserRegisterException
import backend.mwvb.service.UserChangePasswordService
import backend.mwvb.util.Response
import org.springframework.web.bind.annotation.*
import javax.mail.MessagingException
import javax.validation.Valid
import javax.validation.constraints.Email

@RestController
@CrossOrigin
@RequestMapping("/api/v1.0/user/change-password")
class UserChangePasswordController(
    private val changePasswordService: UserChangePasswordService
) {
    @GetMapping("/request/{email}")
    @Throws(UserRegisterException::class, MessagingException::class)
    fun request(@PathVariable("email") @Valid @Email email: String): Response<String> {
        changePasswordService.request(email)
        return Response.success("请查收邮件，修改密码")
    }

    @PostMapping("/complete")
    fun complete(@RequestBody resetPasswordInfo: ResetPasswordInfo): Response<String> {
        changePasswordService.complete(resetPasswordInfo)
        return Response.success("修改密码成功，请登录")
    }
}