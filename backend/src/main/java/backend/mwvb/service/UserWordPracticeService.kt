package backend.mwvb.service

import backend.mwvb.entity.UserWordActivity
import backend.mwvb.entity.UserWordPractice

interface UserWordPracticeService {
    fun add(practice: UserWordPractice)
    fun allPracticesOfWord(wordId: Int): List<UserWordPractice>
}