package kr.hs.emirim.evie.testmateloginpage

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Calendar
import com.google.android.material.bottomsheet.BottomSheetDialog
import kr.hs.emirim.evie.testmateloginpage.goalList.GoalListActivity
import kr.hs.emirim.evie.testmateloginpage.goalList.GoalsListViewModel
import kr.hs.emirim.evie.testmateloginpage.goalList.GoalsListViewModelFactory
import kr.hs.emirim.evie.testmateloginpage.goalList.data.Goal
import kr.hs.emirim.evie.testmateloginpage.subject.AddSubjectActivity
import kr.hs.emirim.evie.testmateloginpage.subject.SUBJECT_NAME
import kr.hs.emirim.evie.testmateloginpage.subject.SubjectHomeAdapter
import kr.hs.emirim.evie.testmateloginpage.subject.SubjectsListVIewModel
import kr.hs.emirim.evie.testmateloginpage.subject.SubjectsListViewModelFactory
import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject

class HomeActivity : AppCompatActivity() {
    lateinit var navHome : ImageButton
    lateinit var navGoal : ImageButton
    lateinit var navCal : ImageButton

    lateinit var addSubjectBtn : ImageButton

    private val SubjectsListVIewModel by viewModels<SubjectsListVIewModel> {
        SubjectsListViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        supportActionBar?.hide()

        addSubjectBtn = findViewById(R.id.addSubjectBtn)
        val subjectsAdapter = SubjectHomeAdapter { subject -> adapterOnClick(subject) } // TODO
        val recyclerView: RecyclerView = findViewById(R.id.subjectRecyclerView)

        recyclerView.adapter = subjectsAdapter

        SubjectsListVIewModel.subjectsLiveData.observe(
            // observer : 어떤 이벤트가 일어난 순간, 이벤트를 관찰하던 관찰자들이 바로 반응하는 패턴
            this,
        ) {
            it?.let { // goalsLiveData의 값이 null이 아닐 때 중괄호 코드 실행
                subjectsAdapter.submitList(it as MutableList<Subject>) // 어댑터 내의 데이터를 새 리스트로 업데이트하는 데 사용
            }
        }

        val listView = findViewById<RecyclerView>(R.id.subjectRecyclerView)
        listView.setHasFixedSize(true)

        addSubjectBtn.setOnClickListener {
            val intent = Intent(this@HomeActivity, AddSubjectActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        navHome = findViewById(R.id.nav_home)
        navGoal = findViewById(R.id.nav_goal)
        navCal = findViewById(R.id.nav_cal)

        navHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }
        navGoal.setOnClickListener {
            val intent = Intent(this, GoalListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }
        navCal.setOnClickListener {
            val intent = Intent(this, Calendar::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }
    } // onCreate

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
        // TODO : 과목별 화면으로 이동
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        /* Inserts flower into viewModel. */
        if (resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val subjectName = data.getStringExtra(SUBJECT_NAME)

                SubjectsListVIewModel.insertSubject(subjectName) ///////////////////////////////////////// insertFlower
            }
        }
    }
}