package kr.hs.emirim.evie.testmateloginpage.alarm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.emirim.evie.testmateloginpage.api.AlarmRepository

class AlarmViewModel(private val alarmRepository: AlarmRepository) : ViewModel() {

    fun getNewAlarms(userId: Long) = alarmRepository.getNewAlarmList()

    fun getRecentAlarms(userId: Long) = alarmRepository.getRecentAlarmList()

    fun fetchNewAlarms(userId: Long) {
        alarmRepository.getNewAlarm(userId)
    }

    fun fetchRecentAlarms(userId: Long) {
        alarmRepository.getRecentAlarms(userId)
    }
}

class AlarmViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlarmViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlarmViewModel(
                alarmRepository = AlarmRepository.getDataSource()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
