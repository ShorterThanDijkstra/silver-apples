package backend.mwvb.controller

import backend.mwvb.entity.User
import backend.mwvb.entity.UserWordPractice
import backend.mwvb.mapper.UserMapper
import backend.mwvb.mapper.UserWordPracticeMapper
import backend.mwvb.mapper.WordMapper
import backend.mwvb.service.UserLoginService
import backend.mwvb.service.UserRegisterService
import backend.mwvb.test_util.RestAssuredTestUtil.assertBody
import backend.mwvb.test_util.RestAssuredTestUtil.getWithTokenSuccessfully
import backend.mwvb.test_util.UserTestUtil
import backend.mwvb.test_util.randomSentence
import org.apache.commons.lang3.RandomStringUtils
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.context.TestPropertySource
import java.sql.Date
import java.time.LocalDate
import java.time.OffsetDateTime


@TestPropertySource(locations = ["classpath:test.properties"])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
internal class UserStatisticControllerTest {
    @Autowired
    private lateinit var wordMapper: WordMapper

    @Autowired
    private lateinit var userRegisterService: UserRegisterService

    @Autowired
    private lateinit var userLoginService: UserLoginService

    @Autowired
    private lateinit var userWordPracticeMapper: UserWordPracticeMapper

    @Autowired
    private lateinit var userMapper: UserMapper

    private lateinit var token: String

    private lateinit var user: User

    companion object {
        const val API: String = "http://localhost:8080/api/v1.0/user-statistic"
    }


    @BeforeEach
    internal fun setUp() {
        val randomUser = UserTestUtil.registerRandomUser(userRegisterService)
        token = UserTestUtil.login(userLoginService, randomUser)["token"].toString()
        user = userMapper.queryUserByName(randomUser.username)
    }


    @Test
    fun wordActivities() {
        val word = wordMapper.queryWordBySpell("elevation")
        val newPractice = UserWordPractice(user, word, randomSentence(), OffsetDateTime.now())
        userWordPracticeMapper.insert(newPractice)

        val response = getWithTokenSuccessfully("${API}/word-activities/${user.username}", token)

        assertBody(response, "data[0].date", equalTo(Date.valueOf(LocalDate.now()).toString()))
    }

    @Test
    fun wordActivitiesWithBadUsername() {
        val word = wordMapper.queryWordBySpell("elevation")

        val newPractice = UserWordPractice(user, word, randomSentence(), OffsetDateTime.now())
        userWordPracticeMapper.insert(newPractice)

        val badUsername = RandomStringUtils.randomAlphabetic(3, 5)
        val response = getWithTokenSuccessfully("${API}/word-activities/${badUsername}", token)

        assertBody(response, "code", equalTo(HttpStatus.BAD_REQUEST.value()))
    }

    @Test
    fun wordPracticesOfUser() {
        val word = wordMapper.queryWordBySpell("elevation")
        val newPractice = UserWordPractice(user, word, randomSentence(), OffsetDateTime.now())
        userWordPracticeMapper.insert(newPractice)

        val response = getWithTokenSuccessfully("${API}/words/${user.username}", token)

        assertBody(response, "data", hasSize<Int>(greaterThan(0)));
    }
}