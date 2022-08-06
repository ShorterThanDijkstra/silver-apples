package backend.mwvb.service.impl

import backend.mwvb.entity.AuthUser
import backend.mwvb.entity.UserCustomWord
import backend.mwvb.exception.UserCustomWordException
import backend.mwvb.mapper.UserCustomWordMapper
import backend.mwvb.service.UserCustomWordService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import java.time.OffsetDateTime

@Service
class UserCustomWordServiceImpl
    (
    private val userCustomWordMapper: UserCustomWordMapper,
) : UserCustomWordService {
    override fun add(userCustomWord: UserCustomWord) {
        userCustomWord.createTime = OffsetDateTime.now()
        val authUser = SecurityContextHolder.getContext().authentication.principal as AuthUser
        userCustomWord.userId = authUser.user.id

        validate(userCustomWord)

        userCustomWordMapper.insert(userCustomWord)
    }

    override fun customWordsOfCurrentUser(): List<UserCustomWord> {
        val authUser = SecurityContextHolder.getContext().authentication.principal as AuthUser
        return userCustomWordMapper.queryByUseId(authUser.user.id)
    }

    override fun deleteCustomWordOffCurrent(wordId: Int) {
        userCustomWordMapper.delete(wordId)
    }


    private fun validate(userCustomWord: UserCustomWord) {
        with(userCustomWord) {
            when {
                userId == null -> throw UserCustomWordException("Bad Word")
                spell == null -> throw UserCustomWordException("Bad Word")
                explanation == null || explanation.isBlank() -> throw UserCustomWordException("Bad Word")
                createTime == null -> throw UserCustomWordException("Bad Word")
                else -> Unit
            }
        }
    }
}