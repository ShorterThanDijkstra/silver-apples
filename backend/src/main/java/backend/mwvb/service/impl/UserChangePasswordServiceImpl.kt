package backend.mwvb.service.impl

import backend.mwvb.exception.UserChangePasswordException
import backend.mwvb.mapper.UserMapper
import backend.mwvb.service.EmailService
import backend.mwvb.service.UserChangePasswordService
import backend.mwvb.service.UserChangePasswordService.Companion.CHANGE_PASSWORD_JWT_CLAIMS_KEY
import backend.mwvb.service.UserChangePasswordService.Companion.CHANGE_PASSWORD_JWT_SUBJECT
import backend.mwvb.service.UserChangePasswordService.Companion.CHANGE_PASSWORD_INFO_EXPIRE
import backend.mwvb.util.CommonJWTUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.mail.internet.AddressException
import javax.mail.internet.InternetAddress

@Service
class UserChangePasswordServiceImpl(
    private val emailService: EmailService,
    @Value("\${com.maerd_zinbiel.silver-apples.jwt.password}")
    private val jwtKey: String,
    private val userMapper: UserMapper
) : UserChangePasswordService {

    private fun creatToken(email: String): String =
        CommonJWTUtils.create(
            CHANGE_PASSWORD_JWT_SUBJECT,
            jwtKey,
            CHANGE_PASSWORD_INFO_EXPIRE,
            mapOf(CHANGE_PASSWORD_JWT_CLAIMS_KEY to email)
        )


    override fun request(email: String) {
        validateEmail(email)
        val token = creatToken(email)
        emailService.sendChangePasswordEmail(token, email)
    }

    override fun complete() {
    }

    private fun validateEmail(email: String) {
        try {
            val emailAddr = InternetAddress(email)
            emailAddr.validate()
        } catch (e: AddressException) {
            throw UserChangePasswordException("邮箱格式不正确: $email")
        }
        if (!emailExist(email)) {
            throw UserChangePasswordException("此邮箱不存在: $email")
        }
    }

    private fun emailExist(email: String): Boolean = userMapper.emailExist(email);
}
