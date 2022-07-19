package backend.mwvb.controller

import backend.mwvb.exception.UserRegisterException
import backend.mwvb.service.UserChangePasswordService
import backend.mwvb.util.Response
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.mail.MessagingException
import javax.validation.Valid
import javax.validation.constraints.Email

@RestController
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
}