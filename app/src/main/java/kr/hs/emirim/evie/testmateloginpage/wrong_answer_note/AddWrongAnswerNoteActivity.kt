package kr.hs.emirim.evie.testmateloginpage.wrong_answer_note

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.Calendar
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.subject.GoalMainListActivity
import kr.hs.emirim.evie.testmateloginpage.home.HomeActivity

class AddWrongAnswerNoteActivity : AppCompatActivity() {

    lateinit var btnGradeDialog: Button
    lateinit var addBtn : android.widget.Button

    lateinit var navHome : ImageButton
    lateinit var navWrong : ImageButton
    lateinit var navGoal : ImageButton
    lateinit var navCal : ImageButton

    lateinit var pre: SharedPreferences

    var arrGrade = arrayOf("중학교 1학년", "중학교 2학년", "중학교 3학년", "고등학교 1학년", "고등학교 2학년", "고등학교 3학년")

    private var selectedReason: String? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wrong_answer_note_add)

        var beforeBtn = findViewById<ImageView>(R.id.before)
//        var WrongList = findViewById<LinearLayout>(R.id.wrong)

        beforeBtn.setOnClickListener{
            onBackPressed();
        }

        btnGradeDialog = findViewById(R.id.signup_grade)
        pre = getSharedPreferences("UserInfo", MODE_PRIVATE)
        val editor = pre.edit()

        btnGradeDialog.setOnClickListener {
            val dlg = AlertDialog.Builder(this@AddWrongAnswerNoteActivity, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
            dlg.setTitle("학년정보")
            dlg.setItems(arrGrade) { dialog, index ->
                btnGradeDialog.text = arrGrade[index]
                editor.putString("usergrade", arrGrade[index])
                editor.apply()
            }
            dlg.setNegativeButton("닫기") { dialog, which ->
                dialog.dismiss()
            }
            dlg.create().show()
        }

        val buttonMistake: Button = findViewById(R.id.mistake_btn)
        val buttonTimeout: Button = findViewById(R.id.timeout_btn)
        val buttonLackConcept: Button = findViewById(R.id.lack_concept_btn)

        val buttons = listOf(buttonMistake, buttonTimeout, buttonLackConcept)

        buttonMistake.setOnClickListener { handleButtonClick(buttonMistake, buttons) }
        buttonTimeout.setOnClickListener { handleButtonClick(buttonTimeout, buttons) }
        buttonLackConcept.setOnClickListener { handleButtonClick(buttonLackConcept, buttons) }

        addBtn = findViewById(R.id.addBtn)
        addBtn.setOnClickListener {
//            WrongList.visibility = View.VISIBLE
            onBackPressed()
        }

// navgation
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
            val intent = Intent(this, WrongAnswerListActivity::class.java)
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

    private fun handleButtonClick(selectedButton: Button, buttons: List<Button>) {
        for (button in buttons) {
            if (button == selectedButton) {
                button.setBackgroundResource(R.drawable.bg_green_view)
                button.setTextColor(resources.getColor(R.color.white))
                selectedReason = button.text.toString()
                Log.d("wrongAnswer", selectedReason!!)
            } else {
                button.setBackgroundResource(R.drawable.bg_white_view)
                button.setTextColor(resources.getColor(R.color.black_300))
            }
        }
    }
}