package backend.mwvb.controller

import backend.mwvb.entity.ResetPasswordInfo
import backend.mwvb.entity.User
import backend.mwvb.mapper.UserMapper
import backend.mwvb.service.UserRegisterService
import backend.mwvb.test_util.RestAssuredTestUtil.*
import backend.mwvb.test_util.UserTestUtil.randomEmail
import backend.mwvb.test_util.UserTestUtil.registerRandomUser
import backend.mwvb.util.CommonJWTUtils
import io.restassured.response.ValidatableResponse
import org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class UserChangePasswordControllerTest {
    @Autowired
    private lateinit var userRegisterService: UserRegisterService

    @Autowired
    private lateinit var userMapper: UserMapper

    @Autowired
    private lateinit var passwordEncoder: PasswordEncoder

    companion object {
        const val API: String = "http://localhost:8080/api/v1.0/user/change-password"
    }

    @Test
    fun request() {
        val user: User = registerRandomUser(userRegisterService)
        val response: ValidatableResponse = getWithoutTokenSuccessfully("${API}/request/${user.email}")
        assertBody(response, "data", equalTo("请查收邮件，修改密码"))
    }

    @Test
    fun requestWithNonexistentEmail() {
        val email = randomEmail();
        val response: ValidatableResponse = getWithoutTokenSuccessfully("${API}/request/${email}")
        assertBody(response, "code", equalTo(HttpStatus.BAD_REQUEST.value()), "data", equalTo("此邮箱不存在: $email"))
    }

    @Test
    internal fun complete() {
        val user: User = registerRandomUser(userRegisterService)
        getWithoutTokenSuccessfully("${API}/request/${user.email}")

        val newPassword = randomAlphanumeric(10, 15)
        val token = CommonJWTUtils.lastToken()
        val completeInfo = ResetPasswordInfo(newPassword, newPassword, token)
        val response = postWithoutTokenSuccessfully("${API}/complete", completeInfo)
        assertBody(response, "data", equalTo("修改密码成功，请登录"))

        val updatedUser = userMapper.queryUserByEmail(user.email)
        assertThat(passwordEncoder.matches(newPassword, updatedUser.password), `is`(true))
    }
}