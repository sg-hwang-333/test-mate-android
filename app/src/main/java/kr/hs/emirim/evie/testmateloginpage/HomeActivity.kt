package kr.hs.emirim.evie.testmateloginpage

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Calendar
import com.google.android.material.bottomsheet.BottomSheetDialog
import kr.hs.emirim.evie.testmateloginpage.goalList.GoalsListViewModel
import kr.hs.emirim.evie.testmateloginpage.goalList.GoalsListViewModelFactory
import kr.hs.emirim.evie.testmateloginpage.goalList.data.Goal

class HomeActivity : AppCompatActivity() {
    lateinit var navHome : ImageButton
    lateinit var navGoal : ImageButton
    lateinit var navCal : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()

        navHome = findViewById(R.id.nav_home)
        navGoal = findViewById(R.id.nav_goal)
        navCal = findViewById(R.id.nav_cal)

        navHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
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