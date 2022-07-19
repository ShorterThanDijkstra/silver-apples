package backend.mwvb.service

interface UserChangePasswordService {
    companion object {
        const val CHANGE_PASSWORD_INFO_EXPIRE = 60 * 60 * 24 * 1000L;
        const val CHANGE_PASSWORD_JWT_SUBJECT = "USER-CHANGE-PASSWORD"
        const val CHANGE_PASSWORD_JWT_CLAIMS_KEY = "USER-EMAIL"
    }
    fun request(email: String)
    fun complete()
}