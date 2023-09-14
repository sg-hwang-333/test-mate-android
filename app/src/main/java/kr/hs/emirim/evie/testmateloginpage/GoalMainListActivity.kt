package kr.hs.emirim.evie.testmateloginpage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Calendar
import kr.hs.emirim.evie.testmateloginpage.goalList.GoalListActivity
import kr.hs.emirim.evie.testmateloginpage.home.HomeActivity
import kr.hs.emirim.evie.testmateloginpage.subject.GoalSubjectTabAdapter
import kr.hs.emirim.evie.testmateloginpage.subject.SUBJECT_NAME
import kr.hs.emirim.evie.testmateloginpage.subject.SubjectHomeAdapter
import kr.hs.emirim.evie.testmateloginpage.subject.SubjectsListVIewModel
import kr.hs.emirim.evie.testmateloginpage.subject.SubjectsListViewModelFactory
import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject

class GoalMainListActivity : AppCompatActivity() {
    lateinit var navHome : ImageButton
    lateinit var navGoal : ImageButton
    lateinit var navCal : ImageButton

    private val SubjectsListVIewModel by viewModels<SubjectsListVIewModel> {
        SubjectsListViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goal_main_page)
        supportActionBar?.hide()

        val goalSubjectTabAdapter = GoalSubjectTabAdapter { subject -> adapterOnClick(subject) } // TODO
        val recyclerView: RecyclerView = findViewById(R.id.goalMainRecyclerView)

        recyclerView.adapter = goalSubjectTabAdapter

        SubjectsListVIewModel.subjectsLiveData.observe(
            // observer : 어떤 이벤트가 일어난 순간, 이벤트를 관찰하던 관찰자들이 바로 반응하는 패턴
            this,
        ) {
            it?.let { // goalsLiveData의 값이 null이 아닐 때 중괄호 코드 실행
                goalSubjectTabAdapter.submitList(it as MutableList<Subject>) // 어댑터 내의 데이터를 새 리스트로 업데이트하는 데 사용
            }
        }

        val listView = findViewById<RecyclerView>(R.id.goalMainRecyclerView)
        listView.setHasFixedSize(true)

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

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action === MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager =
                        getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    private fun adapterOnClick(subject: Subject) {
        val intent = Intent(this, GoalListActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent)
    }

}