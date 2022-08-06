package backend.mwvb.service

import backend.mwvb.entity.UserCustomWord

interface UserCustomWordService {
    fun add(userCustomWord: UserCustomWord)
    fun customWordsOfCurrentUser(): List<UserCustomWord>
    fun deleteCustomWordOffCurrent(wordId: Int)
}