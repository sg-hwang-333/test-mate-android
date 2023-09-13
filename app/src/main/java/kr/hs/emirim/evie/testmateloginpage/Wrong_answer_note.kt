package kr.hs.emirim.evie.testmateloginpage

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Calendar

class Wrong_answer_note : AppCompatActivity() {

    var addPage : Button = findViewById(R.id.addBtn)


    var navHome : ImageButton = findViewById(R.id.nav_home)
    var navGoal : ImageButton = findViewById(R.id.nav_goal)
    var navCal : ImageButton = findViewById(R.id.nav_cal)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wrong_answer_note)

        addPage.setOnClickListener {
            val intent = Intent(this, Wrong_answer_note_add::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }

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