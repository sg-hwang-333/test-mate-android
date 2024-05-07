package kr.hs.emirim.evie.testmateloginpage

import kr.hs.emirim.evie.testmateloginpage.login.LoginRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

data class LoginResponse(val message: String)

data class GoalListResponse(val message : String)

interface TMService {
    @POST("/api/login")
    fun requestLogin(@Body loginData: LoginRequest) : Call<LoginResponse>

    @GET("/goal/get")
    fun requestGoalGet(@Body goalListData : Map<String, Long>) : Call<GoalListResponse>

    @DELETE("/goal/delete")
    fun requestGoalDelete(@Body goalListData : Map<String, Long>) : Call<GoalListResponse>
}