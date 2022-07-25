package backend.mwvb.service.impl

import backend.mwvb.entity.UserWordActivity
import backend.mwvb.entity.UserWordPractice
import backend.mwvb.exception.UserInfoValidateException
import backend.mwvb.mapper.UserMapper
import backend.mwvb.mapper.UserWordPracticeMapper
import backend.mwvb.service.UserStatisticService
import org.springframework.stereotype.Service

@Service
class UserStatisticServiceImpl(
    private val userMapper: UserMapper,
    private val userWordPracticeMapper: UserWordPracticeMapper
) : UserStatisticService {
    override fun wordActivities(username: String): List<UserWordActivity> {
        validate(username)
        return userWordPracticeMapper.queryActivitiesByUsername(username)
    }

    override fun wordPracticesOfUser(username: String): List<UserWordPractice> {
        validate(username)
        return userWordPracticeMapper.wordsPracticesOfUser(username)
    }

    private fun validate(username: String) {
        if (!userMapper.usernameExist(username)) {
            throw UserInfoValidateException("User does not exist")
        }
    }
}