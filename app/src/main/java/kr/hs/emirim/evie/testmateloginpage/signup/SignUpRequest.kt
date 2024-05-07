package kr.hs.emirim.evie.testmateloginpage.signup

data class SignUpRequest(
    val userId: String,
    val name: String,
    val email: String,
    val grade: Int,
    val password: String
)