package kr.hs.emirim.evie.testmateloginpage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Calendar
import kr.hs.emirim.evie.testmateloginpage.subject.GoalMainListActivity
import kr.hs.emirim.evie.testmateloginpage.home.HomeActivity

class Wrong_answer_note : AppCompatActivity() {

    lateinit var addPage : Button

    lateinit var navHome : ImageButton
    lateinit var navWrong : ImageButton
    lateinit var navGoal : ImageButton
    lateinit var navCal : ImageButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wrong_answer_note)
        addPage = findViewById(R.id.addBtn)
        addPage.setOnClickListener {
            val intent = Intent(this, Wrong_answer_note_add::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }

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