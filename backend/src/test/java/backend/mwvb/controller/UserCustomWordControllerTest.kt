package backend.mwvb.controller

import backend.mwvb.entity.User
import backend.mwvb.entity.UserCustomWord
import backend.mwvb.mapper.UserCustomWordMapper
import backend.mwvb.mapper.UserMapper
import backend.mwvb.service.UserLoginService
import backend.mwvb.service.UserRegisterService
import backend.mwvb.test_util.RestAssuredTestUtil.*
import backend.mwvb.test_util.UserTestUtil.login
import backend.mwvb.test_util.UserTestUtil.registerRandomUser
import org.apache.commons.lang3.RandomStringUtils.randomAlphabetic
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.greaterThan
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.TestPropertySource


@TestPropertySource(locations = ["classpath:test.properties"])
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
internal class UserCustomWordControllerTest {
    @Autowired
    private lateinit var userRegisterService: UserRegisterService

    @Autowired
    private lateinit var userLoginService: UserLoginService

    @Autowired
    private lateinit var userCustomWordMapper: UserCustomWordMapper

    @Autowired
    private lateinit var userMapper: UserMapper

    private lateinit var user: User

    private lateinit var token: String

    private lateinit var userCustomWord: UserCustomWord

    companion object {
        const val API: String = "http://localhost:8080/api/v1.0/user/custom-word"
    }

    @BeforeEach
    internal fun setUp() {
        val randomUser = registerRandomUser(userRegisterService)
        user = userMapper.queryUserByName(randomUser.username)
        user.password = randomUser.password
        token = login(userLoginService, user)["token"]!!
        userCustomWord = genUserCustomWord()
    }

    @Test
    fun addWordThenQuery() {
        val response = postWithTokenSuccessfully("$API/", token, userCustomWord)

        assertBody(response, "data", equalTo("上传成功"))

        val customWords = userCustomWordMapper.queryByUseId(user.id)
        val find = find(customWords)

        assertThat(find, `is`(notNullValue()))
    }

    private fun find(words: List<UserCustomWord>): UserCustomWord? {
        return words.find {
            it.spell == userCustomWord.spell &&
                    it.explanation == userCustomWord.explanation &&
                    it.sentence == userCustomWord.sentence &&
                    it.note == userCustomWord.note
        }
    }

    @Test
    fun customWordsOfCurrentUserAfterAdd() {
        postWithTokenSuccessfully("$API/", token, userCustomWord)
        val response = getWithTokenSuccessfully("$API/", token)
        assertBody(response, "data.size()", `is`(greaterThan(0)))
    }

    @Test
    fun delete() {
        postWithTokenSuccessfully("$API/", token, userCustomWord)
        val customWords = userCustomWordMapper.queryByUseId(user.id)
        val find = find(customWords)
        val wordId = find!!.id!!
        val response = deleteWithTokenSuccessfully("$API/${wordId}", token)
        assertBody(response, "data", equalTo("删除成功"))

        val wordsAfterDelete: List<UserCustomWord> = userCustomWordMapper.queryByUseId(user.id)
        assertThat(wordsAfterDelete, hasSize(0))
    }

    private fun genUserCustomWord(): UserCustomWord {
        val userCustomWord = UserCustomWord()
        userCustomWord.spell = randomAlphabetic(2, 5)
        userCustomWord.explanation = randomAlphabetic(6, 10)
        userCustomWord.sentence = randomAlphabetic(6, 10)
        userCustomWord.note = randomAlphabetic(15, 20)
        return userCustomWord
    }
}