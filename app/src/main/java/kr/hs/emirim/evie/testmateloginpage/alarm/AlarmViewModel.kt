package kr.hs.emirim.evie.testmateloginpage.alarm

import androidx.lifecycle.ViewModel
import kr.hs.emirim.evie.testmateloginpage.api.AlarmRepository

class AlarmViewModel(private val alarmRepository: AlarmRepository) : ViewModel() {

    fun getNewAlarms() = alarmRepository.newAlarmListData

    fun getRecentAlarms() = alarmRepository.recentAlarmListData

    fun fetchNewAlarms() {
        alarmRepository.getNewAlarm()
    }

    fun fetchRecentAlarms() {
        alarmRepository.getRecentAlarms()
    }
}
