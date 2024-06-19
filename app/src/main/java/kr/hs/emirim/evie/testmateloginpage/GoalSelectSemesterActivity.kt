package kr.hs.emirim.evie.testmateloginpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import kr.hs.emirim.evie.testmateloginpage.goalList.GoalListActivity
import kr.hs.emirim.evie.testmateloginpage.subject.GoalMainListActivity
import kr.hs.emirim.evie.testmateloginpage.subject.data.SubjectResponse

class GoalSelectSemesterActivity : AppCompatActivity() {
    private lateinit var before: ImageView
    private lateinit var headerTitleTextView: TextView
    private lateinit var textViewTitle: TextView
    private lateinit var semester1Button: Button
    private lateinit var semester2Button: Button
    private lateinit var semester3Button: Button
    private lateinit var semester4Button: Button

    private lateinit var currentSubject : SubjectResponse
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goal_select_semester)
        supportActionBar?.hide()

        currentSubject = intent.getSerializableExtra("selectedNote") as SubjectResponse

        // View 초기화
        before = findViewById(R.id.before)
        headerTitleTextView = findViewById(R.id.header_title)
        textViewTitle = findViewById(R.id.textViewTitle)
        semester1Button = findViewById(R.id.semester1)
        semester2Button = findViewById(R.id.semester2)
        semester3Button = findViewById(R.id.semester3)
        semester4Button = findViewById(R.id.semester4)

        // 헤더 설정
        before.setOnClickListener{
            finish()
            overridePendingTransition(0, 0)
        }

        headerTitleTextView.text = currentSubject.subjectName + " 학습목표"

        // 학기 버튼
        semester1Button.setOnClickListener {
            createIntentAndStartActivity(1)
        }

        semester2Button.setOnClickListener {
            createIntentAndStartActivity(2)
        }

        semester3Button.setOnClickListener {
            createIntentAndStartActivity(3)
        }

        semester4Button.setOnClickListener {
            createIntentAndStartActivity(4)
        }

    }

    fun createIntentAndStartActivity(semester: Int) {
        val intent = Intent(this, GoalListActivity::class.java)
        intent.putExtra("semester", semester)
        intent.putExtra("currentSubject", currentSubject)

        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent)
    }
}