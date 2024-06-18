package kr.hs.emirim.evie.testmateloginpage.alarm

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.hs.emirim.evie.testmateloginpage.R

class AlarmActivity : AppCompatActivity() {

    private val alarmViewModel: AlarmViewModel by viewModels { AlarmViewModelFactory(this) }
    private lateinit var newAlarmAdapter: NewAlarmAdapter
    private lateinit var recentAlarmAdapter: RecentAlarmAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm_page)

        // RecyclerView 설정
        val newAlarmRecyclerView = findViewById<RecyclerView>(R.id.newAlarmRecyclerView)
        val recentAlarmRecyclerView = findViewById<RecyclerView>(R.id.recentAlarmRecyclerView)

        newAlarmAdapter = NewAlarmAdapter(this)
        recentAlarmAdapter = RecentAlarmAdapter(this)

        newAlarmRecyclerView.layoutManager = LinearLayoutManager(this)
        newAlarmRecyclerView.adapter = newAlarmAdapter

        recentAlarmRecyclerView.layoutManager = LinearLayoutManager(this)
        recentAlarmRecyclerView.adapter = recentAlarmAdapter

        // ViewModel에서 데이터 가져오기 및 관찰
        alarmViewModel.getNewAlarms().observe(this, Observer { alarms ->
            Log.d("AlarmActivity", "New Alarms observed: $alarms")
            newAlarmAdapter.submitList(alarms)
        })

        alarmViewModel.getRecentAlarms().observe(this, Observer { alarms ->
            Log.d("AlarmActivity", "Recent Alarms observed: $alarms")
            recentAlarmAdapter.submitList(alarms)
        })

        // 데이터 로드
        alarmViewModel.fetchNewAlarms()
        alarmViewModel.fetchRecentAlarms()

        // 뒤로가기 버튼 설정
        val backBtn = findViewById<ImageView>(R.id.backBtn)
        backBtn.setOnClickListener {
            finish()
        }
    }
}
