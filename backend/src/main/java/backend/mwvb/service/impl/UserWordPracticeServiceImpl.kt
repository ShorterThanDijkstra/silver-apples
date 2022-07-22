package backend.mwvb.service.impl

import backend.mwvb.entity.AuthUser
import backend.mwvb.entity.UserWordPractice
import backend.mwvb.exception.UserWordPracticeException
import backend.mwvb.mapper.UserMapper
import backend.mwvb.mapper.UserWordPracticeMapper
import backend.mwvb.mapper.WordMapper
import backend.mwvb.service.UserWordPracticeService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.Clock
import java.time.OffsetDateTime

@Service
class UserWordPracticeServiceImpl(
    private val userWordPracticeMapper: UserWordPracticeMapper,
    private val userMapper: UserMapper,
    private val wordMapper: WordMapper,
) :
    UserWordPracticeService {
    override fun add(practice: UserWordPractice) {
        validate(practice)
        practice.createTime = OffsetDateTime.now(Clock.systemUTC())
        userWordPracticeMapper.insert(practice)
    }

    override fun allPracticesOfWord(wordId: Int): List<UserWordPractice> {
        if (!wordMapper.idExist(wordId)) {
            throw UserWordPracticeException("Bad Word")
        }
        return userWordPracticeMapper.queryByWordId(wordId)
    }


    private fun validate(practice: UserWordPractice) {
        val authUser = SecurityContextHolder.getContext().authentication.principal as AuthUser
        practice.user = authUser.user
        with(practice) {
            when {
                user == null || user?.id == null -> throw UserWordPracticeException("Bad User")
                word == null || word.id == null -> throw UserWordPracticeException("Bad Word")
                sentence.isNullOrBlank() -> throw UserWordPracticeException("Bad Sentence")
                else -> Unit
            }
        }
        if (!userMapper.idExist(practice.user?.id)) {
            throw UserWordPracticeException("Bad User")
        }
        if (!wordMapper.idExist(practice.word.id)) {
            throw UserWordPracticeException("Bad Word")
        }
    }
}