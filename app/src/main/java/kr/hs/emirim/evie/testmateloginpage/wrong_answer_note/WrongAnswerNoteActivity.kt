package kr.hs.emirim.evie.testmateloginpage.wrong_answer_note

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Calendar
import kr.hs.emirim.evie.testmateloginpage.NavigationButtons
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.subject.GoalMainListActivity
import kr.hs.emirim.evie.testmateloginpage.home.HomeActivity
import kr.hs.emirim.evie.testmateloginpage.subject.BOOK_TAG
import kr.hs.emirim.evie.testmateloginpage.subject.SUBJECT_NAME
import kr.hs.emirim.evie.testmateloginpage.subject.SubjectHomeAdapter
import kr.hs.emirim.evie.testmateloginpage.subject.WrongAnswerSubjectAdapter
import kr.hs.emirim.evie.testmateloginpage.subject.WrongAnswerSubjectViewModel
import kr.hs.emirim.evie.testmateloginpage.subject.WrongAnswerSubjectsViewModelFactory
import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject

class WrongAnswerNoteActivity : AppCompatActivity() {

    lateinit var addPage : Button

    lateinit var spinner: Spinner

    private val wrongAnswerSubjectViewModel by viewModels<WrongAnswerSubjectViewModel> {
        WrongAnswerSubjectsViewModelFactory(this)
    }

    private lateinit var navigationButtons: NavigationButtons

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wrong_answer_note)

        // 학년 spiner api 연동
        val pre = getSharedPreferences("UserInfo", MODE_PRIVATE)
        val grade = pre.getString("usergrade", "고등학교 2학년 ") // 기본값 설정

        val facilityList = arrayOf("고등학교 2학년")

        spinner = findViewById(R.id.spinnerWrong)

        val facilityListWithUserGrade = mutableListOf(*facilityList, grade)
        val adapter = ArrayAdapter(this, R.layout.spinner_item, facilityListWithUserGrade)
        // 스피너에 어댑터 설정
        spinner.adapter = adapter

        addPage = findViewById(R.id.addBtn)
        addPage.setOnClickListener {
            val intent = Intent(this, AddWrongAnswerNoteActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }

        val wrongAnswerSubjectAdapter = WrongAnswerSubjectAdapter { subject -> adapterOnClick(subject) }
        val recyclerView: RecyclerView = findViewById(R.id.wrongAnswerSubjectRecyclerView)

        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) // 수평 레이아웃 방향 설정
        recyclerView.adapter = wrongAnswerSubjectAdapter

        wrongAnswerSubjectViewModel.subjectsLiveData.observe(
            // observer : 어떤 이벤트가 일어난 순간, 이벤트를 관찰하던 관찰자들이 바로 반응하는 패턴
            this
        ) {
            it?.let { // goalsLiveData의 값이 null이 아닐 때 중괄호 코드 실행
                wrongAnswerSubjectAdapter.submitList(it as MutableList<Subject>) // 어댑터 내의 데이터를 새 리스트로 업데이트하는 데 사용
            }
        }

        // 네이게이션
        navigationButtons = NavigationButtons(this)
        navigationButtons.initialize(this)
    }

    private fun adapterOnClick(subject: Subject) {
        // TODO : 과목별 화면으로 이동
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        /* Inserts subject into viewModel. */
        if (resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val subjectName = data.getStringExtra(SUBJECT_NAME)
                val subjectImage = data.getStringExtra(BOOK_TAG)

                wrongAnswerSubjectViewModel.insertSubject(subjectName, subjectImage) ///////////////////////////////////////// insertFlower
            }
        }
    }
}