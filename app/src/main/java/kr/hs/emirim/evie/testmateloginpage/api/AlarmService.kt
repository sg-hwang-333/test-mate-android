package kr.hs.emirim.evie.testmateloginpage.api


import kr.hs.emirim.evie.testmateloginpage.alarm.data.NewAlarm
import kr.hs.emirim.evie.testmateloginpage.alarm.data.RecentAlarm
import retrofit2.Call
import retrofit2.http.GET

interface AlarmService {
    @GET("/api/alarm/new")
    fun getNewAlarm(): Call<List<NewAlarm>>

    @GET("/api/alarm/recent")
    fun getRecentAlarm(): Call<List<RecentAlarm>>
}