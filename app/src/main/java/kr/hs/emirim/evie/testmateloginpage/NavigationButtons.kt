package kr.hs.emirim.evie.testmateloginpage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.ImageButton
import com.example.myapplication.Calendar
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.home.HomeActivity
import kr.hs.emirim.evie.testmateloginpage.subject.GoalMainListActivity
import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.WrongAnswerNoteActivity

class NavigationButtons(private val context: Context) {

    private lateinit var navHome: ImageButton
    private lateinit var navWrong: ImageButton
    private lateinit var navGoal: ImageButton
    private lateinit var navCal: ImageButton

    fun initialize(activity: Activity) {
        navHome = activity.findViewById(R.id.nav_home)
        navWrong = activity.findViewById(R.id.nav_wrong)
        navGoal = activity.findViewById(R.id.nav_goal)
        navCal = activity.findViewById(R.id.nav_cal)

        navHome.setOnClickListener {
            val intent = Intent(context, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            context.startActivity(intent)
        }

        navWrong.setOnClickListener {
            val intent = Intent(context, WrongAnswerNoteActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            context.startActivity(intent)
        }

        navGoal.setOnClickListener {
            val intent = Intent(context, GoalMainListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            context.startActivity(intent)
        }

        navCal.setOnClickListener {
            val intent = Intent(context, Calendar::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            context.startActivity(intent)
        }
    }
}