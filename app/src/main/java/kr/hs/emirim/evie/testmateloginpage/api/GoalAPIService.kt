package kr.hs.emirim.evie.testmateloginpage.api

import kr.hs.emirim.evie.testmateloginpage.goalList.data.GoalResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface GoalAPIService {
    @GET("/api/goals/{subjectId}/{semester}")
    fun getGoalListBySemester(@Path("subjectId") subjectId : Int, @Path("semester") semester : Int) : Call<List<GoalResponse>>

    @DELETE("/goal/delete")
    fun requestGoalDelete(@Body goalListData : Map<String, Long>) : Call<GoalResponse>
}