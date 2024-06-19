package kr.hs.emirim.evie.testmateloginpage

import kr.hs.emirim.evie.testmateloginpage.login.LoginRequest
import kr.hs.emirim.evie.testmateloginpage.signup.SignUpRequest
import kr.hs.emirim.evie.testmateloginpage.userData.UserDetailsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

data class LoginResponse(
    val message: String,
    val userDetails: UserDetailsResponse
)
data class SignUpResponse(val message: String)
data class UserCheckResponse(val message: String)

interface TMService {
    @POST("/api/login")
    fun requestLogin(@Body loginData: LoginRequest) : Call<LoginResponse>

    @POST("/api/sign-up")
    fun signUp(@Body signUpReq: SignUpRequest): Call<SignUpResponse>

    @GET("/api/user-check")
    fun checkUserId(@Query("userId") userId: String): Call<UserCheckResponse>
}