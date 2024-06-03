package kr.hs.emirim.evie.testmateloginpage.wrong_answer_note

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.hs.emirim.evie.testmateloginpage.NavigationButtons
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.comm.RetrofitClient
import kr.hs.emirim.evie.testmateloginpage.subject.WrongAnswerSubjectAdapter
import kr.hs.emirim.evie.testmateloginpage.subject.WrongAnswerSubjectViewModel
import kr.hs.emirim.evie.testmateloginpage.subject.WrongAnswerSubjectsViewModelFactory
import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject
import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data.WrongAnswerListResponse
import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data.WrongAnswerRepository

class WrongAnswerListActivity : AppCompatActivity() {

    lateinit var addPage : Button

    lateinit var spinner: Spinner

    private val subjectViewModel by viewModels<WrongAnswerSubjectViewModel> {
        WrongAnswerSubjectsViewModelFactory(this)
    }

    private val listViewModel by viewModels<WrongAnswerListViewModel> {
        WrongAnswerListViewModelFactory(this)
    }

    private lateinit var navigationButtons: NavigationButtons

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wrong_answer_note)

        val APIService = RetrofitClient.create()

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

        // 과목 버튼 recyclerView
        val subjectAdapter = WrongAnswerSubjectAdapter { subject -> adapterOnClick(subject) }
        val subjectRecyclerView: RecyclerView = findViewById(R.id.wrongAnswerSubjectRecyclerView)

        subjectRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) // 수평 레이아웃 방향 설정
        subjectRecyclerView.adapter = subjectAdapter

        subjectViewModel.subjectsLiveData.observe(
            // observer : 어떤 이벤트가 일어난 순간, 이벤트를 관찰하던 관찰자들이 바로 반응하는 패턴
            this
        ) {
            it?.let { // goalsLiveData의 값이 null이 아닐 때 중괄호 코드 실행
                subjectAdapter.submitList(it as MutableList<Subject>) // 어댑터 내의 데이터를 새 리스트로 업데이트하는 데 사용
            }
        }

        // 오답노트 recyclerView
        val listAdapter = WrongAnswerListAdapter { list -> adapterOnClick(list) }
        val listRecyclerView: RecyclerView = findViewById(R.id.wrongAnswerObjectRecyclerView)

        listRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) // 수평 레이아웃 방향 설정
        listRecyclerView.adapter = listAdapter

        listViewModel.wrongAnswerListData.observe(
            // observer : 어떤 이벤트가 일어난 순간, 이벤트를 관찰하던 관찰자들이 바로 반응하는 패턴
            this
        ) {
            it?.let { // goalsLiveData의 값이 null이 아닐 때 중괄호 코드 실행
                listAdapter.submitList(it as MutableList<WrongAnswerListResponse>) // 어댑터 내의 데이터를 새 리스트로 업데이트하는 데 사용
            }
        }

        listViewModel.getLists(3, 1)

        // 네비게이션
        navigationButtons = NavigationButtons(this)
        navigationButtons.initialize(this)
    }

    private fun adapterOnClick(subject: Subject) {
        // TODO : 과목별 데이터 불러오기
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
//        super.onActivityResult(requestCode, resultCode, intentData)
//
//        /* Inserts subject into viewModel. */
//        if (resultCode == Activity.RESULT_OK) {
//            intentData?.let { data ->
//                val subjectName = data.getStringExtra(SUBJECT_NAME)
//                val subjectImage = data.getStringExtra(BOOK_TAG)
//
//                wrongAnswerSubjectViewModel.insertSubject(subjectName, subjectImage) ///////////////////////////////////////// insertFlower
//            }
//        }
//    }

    private fun adapterOnClick(list: WrongAnswerListResponse) {
        // TODO : 해당 오답노트 로딩 화면으로 이동
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
//        super.onActivityResult(requestCode, resultCode, intentData)
//
//        /* Inserts subject into viewModel. */
//        if (resultCode == Activity.RESULT_OK) {
//            intentData?.let { data ->
//                val subjectName = data.getStringExtra(SUBJECT_NAME)
//                val subjectImage = data.getStringExtra(BOOK_TAG)
//
//                wrongAnswerSubjectViewModel.insertSubject(subjectName, subjectImage) ///////////////////////////////////////// insertFlower
//            }
//        }
//    }
}