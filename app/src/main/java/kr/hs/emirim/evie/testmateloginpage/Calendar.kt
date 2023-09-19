package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import kr.hs.emirim.evie.testmateloginpage.AdapterMonth
import kr.hs.emirim.evie.testmateloginpage.subject.GoalMainListActivity
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.Wrong_answer_note
import kr.hs.emirim.evie.testmateloginpage.databinding.ActivityCalendarBinding
import kr.hs.emirim.evie.testmateloginpage.home.HomeActivity

class Calendar : AppCompatActivity() {

    lateinit var navHome : ImageButton
    lateinit var navWrong : ImageButton
    lateinit var navGoal : ImageButton
    lateinit var navCal : ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

// 뷰 바인딩 클래스 가져오기
        val binding = ActivityCalendarBinding.inflate(layoutInflater)
        setContentView(binding.root)

// 뷰 바인딩 클래스에서 RecyclerView에 대한 참조 가져오기
        val calendarCustomRecyclerView = binding.calendarCustom

// 어댑터 및 레이아웃 매니저 초기화
        val monthListAdapter = AdapterMonth()
        val monthListManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        calendarCustomRecyclerView.layoutManager = monthListManager
        calendarCustomRecyclerView.adapter = monthListAdapter

// 스크롤 위치 설정 (예: 중간 위치)
        val initialScrollPosition = Int.MAX_VALUE / 2
        calendarCustomRecyclerView.scrollToPosition(initialScrollPosition)

// PagerSnapHelper 연결
        val snap = PagerSnapHelper()
        snap.attachToRecyclerView(calendarCustomRecyclerView)

        navHome = findViewById(R.id.nav_home)
        navWrong = findViewById(R.id.nav_wrong)
        navGoal = findViewById(R.id.nav_goal)
        navCal = findViewById(R.id.nav_cal)

        navHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }
        navWrong.setOnClickListener {
            val intent = Intent(this, Wrong_answer_note::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }
        navGoal.setOnClickListener {
            val intent = Intent(this, GoalMainListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }
        navCal.setOnClickListener {
            val intent = Intent(this, Calendar::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }

    }
}
