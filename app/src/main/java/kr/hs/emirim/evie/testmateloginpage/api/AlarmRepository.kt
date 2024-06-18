package kr.hs.emirim.evie.testmateloginpage.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import kr.hs.emirim.evie.testmateloginpage.alarm.data.NewAlarm
import kr.hs.emirim.evie.testmateloginpage.alarm.data.RecentAlarm
import kr.hs.emirim.evie.testmateloginpage.comm.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmRepository {

    var newAlarmListData = MutableLiveData<List<NewAlarm>>()
    var recentAlarmListData = MutableLiveData<List<RecentAlarm>>()
    private val alarmService = RetrofitClient.create(AlarmService::class.java)

    fun getNewAlarm() {
        val call = alarmService.getNewAlarm()
        Log.d("AlarmRepository", "Sending request to /api/alarm/new")
        call.enqueue(object : Callback<List<NewAlarm>> {
            override fun onResponse(call: Call<List<NewAlarm>>, response: Response<List<NewAlarm>>) {
                if (response.isSuccessful) {
                    val alarms: List<NewAlarm>? = response.body()
                    alarms?.let {
                        newAlarmListData.postValue(it)
                        Log.d("AlarmRepository", "New Alarms fetched successfully: $alarms")
                    }
                } else {
                    Log.e("AlarmRepository", "Failed to fetch new alarms. Error code: ${response.code()}, Error message: ${response.message()}, Error body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<NewAlarm>>, t: Throwable) {
                Log.e("AlarmRepository", "Failed to fetch new alarms. Error message: ${t.message}")
            }
        })
    }

    fun getRecentAlarms() {
        val call = alarmService.getRecentAlarm()
        Log.d("AlarmRepository", "Sending request to /api/alarm/recent")
        call.enqueue(object : Callback<List<RecentAlarm>> {
            override fun onResponse(call: Call<List<RecentAlarm>>, response: Response<List<RecentAlarm>>) {
                if (response.isSuccessful) {
                    val alarms: List<RecentAlarm>? = response.body()
                    alarms?.let {
                        recentAlarmListData.postValue(it)
                        Log.d("AlarmRepository", "Recent Alarms fetched successfully: $alarms")
                    }
                } else {
                    Log.e("AlarmRepository", "Failed to fetch new alarms. Error code: ${response.code()}, Error message: ${response.message()}, Error body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<List<RecentAlarm>>, t: Throwable) {
                Log.e("AlarmRepository", "Failed to fetch recent alarms. Error message: ${t.message}")
            }
        })
    }

    fun getNewAlarmList(): MutableLiveData<List<NewAlarm>> {
        return newAlarmListData
    }

    fun getRecentAlarmList(): MutableLiveData<List<RecentAlarm>> {
        return recentAlarmListData
    }

    companion object {
        private var INSTANCE: AlarmRepository? = null

        fun getDataSource(): AlarmRepository {
            return synchronized(AlarmRepository::class) {
                val newInstance = INSTANCE ?: AlarmRepository()
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}
