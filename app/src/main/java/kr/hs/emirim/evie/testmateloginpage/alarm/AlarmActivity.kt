package kr.hs.emirim.evie.testmateloginpage.alarm

import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.hs.emirim.evie.testmateloginpage.R

class AlarmActivity : AppCompatActivity() {

    private val alarmViewModel: AlarmViewModel by viewModels { AlarmViewModelFactory(this) }
    private lateinit var newAlarmAdapter: AlarmAdapter
    private lateinit var recentAlarmAdapter: AlarmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm_page)

        // RecyclerView 설정
        val newAlarmRecyclerView = findViewById<RecyclerView>(R.id.newAlarmRecyclerView)
        val recentAlarmRecyclerView = findViewById<RecyclerView>(R.id.recentAlarmRecyclerView)

        newAlarmAdapter = AlarmAdapter(this)
        recentAlarmAdapter = AlarmAdapter(this)

        newAlarmRecyclerView.layoutManager = LinearLayoutManager(this)
        newAlarmRecyclerView.adapter = newAlarmAdapter

        recentAlarmRecyclerView.layoutManager = LinearLayoutManager(this)
        recentAlarmRecyclerView.adapter = recentAlarmAdapter

        // ViewModel에서 데이터 가져오기 및 관찰
        alarmViewModel.getNewAlarms(USER_ID).observe(this, Observer { alarms ->
            newAlarmAdapter.submitList(alarms)
        })

        alarmViewModel.getRecentAlarms(USER_ID).observe(this, Observer { alarms ->
            recentAlarmAdapter.submitList(alarms)
        })

        // 데이터 로드
        alarmViewModel.fetchNewAlarms(USER_ID)
        alarmViewModel.fetchRecentAlarms(USER_ID)

        // 뒤로가기 버튼 설정
        val backBtn = findViewById<ImageView>(R.id.backBtn)
        backBtn.setOnClickListener {
            finish()
        }
    }

    companion object {
        const val USER_ID = 1L // 사용자 ID는 실제 사용 환경에 맞게 설정
    }

}
