package backend.mwvb.service

interface UserValidateService {
    fun validateEmail(email: String)
    fun validatePasswords(password: String, confirmedPassword: String)
    fun validateUsername(username: String)
}