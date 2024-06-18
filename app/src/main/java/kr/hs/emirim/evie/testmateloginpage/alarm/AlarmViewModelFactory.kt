package kr.hs.emirim.evie.testmateloginpage.alarm

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.emirim.evie.testmateloginpage.api.AlarmRepository

class AlarmViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AlarmViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AlarmViewModel(
                alarmRepository = AlarmRepository()
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
