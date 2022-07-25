package backend.mwvb.service

import backend.mwvb.entity.UserWordActivity
import backend.mwvb.entity.UserWordPractice

interface UserStatisticService {
    fun wordActivities(username: String): List<UserWordActivity>
    fun wordPracticesOfUser(username: String): List<UserWordPractice>

}