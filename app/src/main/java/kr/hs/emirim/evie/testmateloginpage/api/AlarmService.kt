package kr.hs.emirim.evie.testmateloginpage.api


import kr.hs.emirim.evie.testmateloginpage.alarm.data.Alarm
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AlarmService {
    @GET("/api/alarm/new")
    fun getNewAlarm(@Query("userId") userId: Long): Call<List<Alarm>>

    @GET("/api/alarm/recent")
    fun getRecentAlarms(@Query("userId") userId: Long): Call<List<Alarm>>
}