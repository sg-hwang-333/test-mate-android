package kr.hs.emirim.evie.testmateloginpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class GoalMainListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goal_main_page)
        supportActionBar?.hide()
    }
}