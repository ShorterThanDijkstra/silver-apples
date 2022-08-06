package backend.mwvb.service.impl

import backend.mwvb.entity.ResetPasswordInfo
import backend.mwvb.exception.UserChangePasswordException
import backend.mwvb.mapper.UserMapper
import backend.mwvb.service.EmailService
import backend.mwvb.service.UserChangePasswordService
import backend.mwvb.service.UserChangePasswordService.Companion.CHANGE_PASSWORD_INFO_EXPIRE
import backend.mwvb.service.UserChangePasswordService.Companion.CHANGE_PASSWORD_JWT_CLAIMS_KEY
import backend.mwvb.service.UserChangePasswordService.Companion.CHANGE_PASSWORD_JWT_SUBJECT
import backend.mwvb.service.UserValidateService
import backend.mwvb.util.CommonJWTUtils
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Lazy
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserChangePasswordServiceImpl(
    private val emailService: EmailService,
    @Value("\${com.maerd_zinbiel.silver-apples.jwt.password}")
    private val jwtKey: String,
    private val userMapper: UserMapper,
    private val userValidateService: UserValidateService,
    private val passwordEncoder: PasswordEncoder
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

    override fun complete(resetPasswordInfo: ResetPasswordInfo) {
        validateInfo(resetPasswordInfo)
        val email = getEmail(resetPasswordInfo.token)
        val newPassword =
            passwordEncoder.encode(resetPasswordInfo.password)
        userMapper.updatePasswordByEmail(email, newPassword)
    }

    private fun validateInfo(info: ResetPasswordInfo) {
        userValidateService.validatePasswords(info.password, info.confirmedPassword)
    }

    private fun getEmail(token: String) =
        CommonJWTUtils.parse(
            token,
            jwtKey,
            CHANGE_PASSWORD_JWT_SUBJECT,
            CHANGE_PASSWORD_JWT_CLAIMS_KEY
        ) { msg -> UserChangePasswordException(msg) }


    private fun validateEmail(email: String) {
        userValidateService.validateEmail(email)
        if (!emailExist(email)) {
            throw UserChangePasswordException("此邮箱不存在: $email")
        }
    }

    private fun emailExist(email: String): Boolean = userMapper.emailExist(email)
}
