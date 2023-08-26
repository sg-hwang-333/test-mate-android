package kr.hs.emirim.evie.testmateloginpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GoalActivity : AppCompatActivity() {

    private lateinit var goalRecycleView : RecyclerView
    lateinit var adapter : GoalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide() // Android 앱의 액션바(작업 표시줄)를 숨기는 코드

        goalRecycleView = findViewById(R.id.goalRecyclerView)
        goalRecycleView.layoutManager = LinearLayoutManager(this)
    }
}