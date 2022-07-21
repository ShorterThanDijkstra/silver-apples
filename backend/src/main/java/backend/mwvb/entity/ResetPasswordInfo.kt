package backend.mwvb.entity

data class ResetPasswordInfo(val password: String, val confirmedPassword: String, val token: String) {
}