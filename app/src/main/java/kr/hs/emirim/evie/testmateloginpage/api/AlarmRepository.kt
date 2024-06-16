package kr.hs.emirim.evie.testmateloginpage.api

import androidx.lifecycle.MutableLiveData
import kr.hs.emirim.evie.testmateloginpage.alarm.data.Alarm
import kr.hs.emirim.evie.testmateloginpage.comm.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AlarmRepository {

    var newAlarmListData = MutableLiveData<List<Alarm>>()
    var recentAlarmListData = MutableLiveData<List<Alarm>>()
    private val alarmService = RetrofitClient.create(AlarmService::class.java)

    fun getNewAlarm(userId: Long) {
        val call = alarmService.getNewAlarm(userId)
        call.enqueue(object : Callback<List<Alarm>> {
            override fun onResponse(call: Call<List<Alarm>>, response: Response<List<Alarm>>) {
                if (response.isSuccessful) {
                    val alarms: List<Alarm>? = response.body()
                    alarms?.let {
                        newAlarmListData.postValue(it)
                    }
                } else {
                    println("Failed to get new alarms. Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Alarm>>, t: Throwable) {
                println("Failed to get new alarms. Error message: ${t.message}")
            }
        })
    }

    fun getRecentAlarms(userId: Long) {
        val call = alarmService.getRecentAlarms(userId)
        call.enqueue(object : Callback<List<Alarm>> {
            override fun onResponse(call: Call<List<Alarm>>, response: Response<List<Alarm>>) {
                if (response.isSuccessful) {
                    val alarms: List<Alarm>? = response.body()
                    alarms?.let {
                        recentAlarmListData.postValue(it)
                    }
                } else {
                    println("Failed to get recent alarms. Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Alarm>>, t: Throwable) {
                println("Failed to get recent alarms. Error message: ${t.message}")
            }
        })
    }

    fun getNewAlarmList(): MutableLiveData<List<Alarm>> {
        return newAlarmListData
    }

    fun getRecentAlarmList(): MutableLiveData<List<Alarm>> {
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
