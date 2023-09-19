package kr.hs.emirim.evie.testmateloginpage

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
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

data class GoalListResponse(val message : String)

interface TMService {
    @POST("/api/login")
    fun requestLogin(@Body loginData: Map<String, String>) : Call<LoginResponse>

    @DELETE("/goal/delete")
    fun requestGoalDelete(@Body goalListData : Map<String, Long>) : Call<GoalListResponse>
}