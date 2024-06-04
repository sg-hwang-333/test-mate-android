package kr.hs.emirim.evie.testmateloginpage.wrong_answer_note

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kr.hs.emirim.evie.testmateloginpage.NavigationButtons
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.comm.RetrofitClient
import kr.hs.emirim.evie.testmateloginpage.subject.WrongAnswerSubjectAdapter
import kr.hs.emirim.evie.testmateloginpage.subject.WrongAnswerSubjectViewModel
import kr.hs.emirim.evie.testmateloginpage.subject.WrongAnswerSubjectsViewModelFactory
import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject
import kr.hs.emirim.evie.testmateloginpage.util.SpinnerUtil.Companion.gradeSpinner
import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data.WrongAnswerAPIService
import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data.WrongAnswerListResponse
import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data.WrongAnswerRepository

class WrongAnswerListActivity : AppCompatActivity() {

    lateinit var addPage : FloatingActionButton

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

        // 학년 spiner api 연동
        spinner = gradeSpinner(this, R.id.spinnerWrong)
        spinner.setSelection(2)
        var selectedPosition = spinner.selectedItemPosition + 1// grade 인덱스 (ex. 3)
        var selectedItem = spinner.getItemAtPosition(selectedPosition).toString() // grade 문자열 (ex. 고등학교 2학년)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // 선택된 항목의 위치(position)를 이용하여 해당 항목의 값을 가져옴
                val selectedItem = parent?.getItemAtPosition(position).toString()
                selectedPosition = position + 1
                // 선택된 항목에 대한 처리 작업 수행
//                Toast.makeText(context, "선택된 항목: $selectedItem", Toast.LENGTH_SHORT).show()
                listViewModel.getLists(selectedPosition, 1, selectedItem)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 아무 것도 선택되지 않았을 때 처리할 작업
            }
        }

        // 오답노트 추가 버튼
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
            it?.let {
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

        listViewModel.getLists(3, 1, selectedItem)

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