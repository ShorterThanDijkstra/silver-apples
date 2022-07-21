package backend.mwvb.service.impl

import backend.mwvb.exception.UserInfoValidateException
import backend.mwvb.service.UserValidateService
import org.springframework.stereotype.Service
import javax.mail.internet.AddressException
import javax.mail.internet.InternetAddress

@Service
class UserValidateServiceImpl : UserValidateService {
    override fun validateEmail(email: String) {
        try {
            val emailAddr = InternetAddress(email)
            emailAddr.validate()
        } catch (e: AddressException) {
            throw UserInfoValidateException("邮箱格式不正确")
        }
    }

    override fun validatePasswords(password: String, confirmedPassword: String) {
        if (password.length < 10) {
            throw UserInfoValidateException("密码长度应至少为10")
        }
        if (password != confirmedPassword) {
            throw UserInfoValidateException("两次密码输入不一致")
        }

    }

    override fun validateUsername(username: String) {
        if (username.trim().length < 5) {
            throw UserInfoValidateException("用户名长度应至少为5")
        }
    }
}