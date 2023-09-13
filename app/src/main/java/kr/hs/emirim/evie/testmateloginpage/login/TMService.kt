package kr.hs.emirim.evie.testmateloginpage.login

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

//interface LoginService {
////    @POST("/api/login")
////    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>
////
////    @POST("/api/register")
////    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>
//
//    @FormUrlEncoded
//    @POST("/login/")
//    fun requestLogin(
//        @Field("email") email:String,
//        @Field("password") password:String
//    ) : Call<Login>
//}

// data class LoginData(val email: String, val password: String)


data class LoginResponse(val message: String)
interface TMService {
    @POST("/api/login")
    fun requestLogin(@Body loginData: Map<String, String>) : Call<LoginResponse>
}