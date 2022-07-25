package backend.mwvb.controller

import backend.mwvb.entity.User
import backend.mwvb.entity.UserWordActivity
import backend.mwvb.entity.UserWordPractice
import backend.mwvb.mapper.UserMapper
import backend.mwvb.mapper.UserWordPracticeMapper
import backend.mwvb.mapper.WordMapper
import backend.mwvb.service.UserLoginService
import backend.mwvb.service.UserRegisterService
import backend.mwvb.test_util.RestAssuredTestUtil.*
import backend.mwvb.test_util.UserTestUtil.login
import backend.mwvb.test_util.UserTestUtil.registerRandomUser
import backend.mwvb.test_util.randomSentence
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.emptyString
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource
import java.sql.Date
import java.time.LocalDate

@TestPropertySource(locations = ["classpath:test.properties"])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
internal class UserWordPracticeControllerTest {
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
        const val API: String = "http://localhost:8080/api/v1.0/book/word-practice"
    }

    @BeforeEach
    internal fun setUp() {
        val randomUser = registerRandomUser(userRegisterService)
        token = login(userLoginService, randomUser)["token"].toString()
        user = userMapper.queryUserByName(randomUser.username)
    }

    @Test
    fun practice() {
        val today = Date.valueOf(LocalDate.now())
        var activities: List<UserWordActivity> = userWordPracticeMapper.queryActivitiesByUserId(user.id)
        val beforeInsert = activities.find { it.date == today }
        val beforeInsertCount = if (beforeInsert == null) 0 else beforeInsert.count
        val word = wordMapper.queryWordBySpell("elevation")
        val newPractice = UserWordPractice(word, randomSentence())
        val response = postWithTokenSuccessfully("$API/practice", token, newPractice)

        assertBody(response, "data", equalTo("上传成功"))

        val practices: List<UserWordPractice> = userWordPracticeMapper.queryByWordId(word.id)
        val match = practices.stream().anyMatch { practice: UserWordPractice ->
            practice.word == newPractice.word &&
                    practice.sentence == newPractice.sentence
        }
        activities = userWordPracticeMapper.queryActivitiesByUserId(user.id)
        val afterInsert = activities.find { it.date == today }
        val afterInsertCount = if (afterInsert == null) 0 else afterInsert.count
        assertThat(afterInsertCount - beforeInsertCount, `is`(1))
        assertThat(match, Matchers.`is`(true))
    }

    @Test
    internal fun allPracticesOfWord() {
        val word = wordMapper.queryWordBySpell("elevation")
        val newPractice = UserWordPractice(word, randomSentence())
        postWithTokenSuccessfully("$API/practice", token, newPractice)
        val response = getWithTokenSuccessfully("$API/practices/${word.id}", token)
        assertBody(response, "data", not(`is`(emptyString())))
    }
}