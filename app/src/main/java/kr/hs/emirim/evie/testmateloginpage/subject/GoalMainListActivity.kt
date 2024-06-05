package kr.hs.emirim.evie.testmateloginpage.subject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ImageButton
import android.widget.Spinner
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Calendar
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.TMService
import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.WrongAnswerListActivity
import kr.hs.emirim.evie.testmateloginpage.comm.RetrofitClient
import kr.hs.emirim.evie.testmateloginpage.goalList.GoalListActivity
import kr.hs.emirim.evie.testmateloginpage.home.HomeActivity
import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
class GoalMainListActivity : AppCompatActivity() {
    private lateinit var navHome: ImageButton
    private lateinit var navWrong: ImageButton
    private lateinit var navGoal: ImageButton
    private lateinit var navCal: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var gradeTagSpinner: Spinner
    private val apiService = RetrofitClient.create(TMService::class.java)
    private var selectedGradeIndex: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goal_main_page)
        supportActionBar?.hide()

        initView()
        setupListeners()
        fetchSubjectsByGrade(selectedGradeIndex)
    }

    private fun initView() {
        // UI 요소 초기화
        navHome = findViewById(R.id.nav_home)
        navWrong = findViewById(R.id.nav_wrong)
        navGoal = findViewById(R.id.nav_goal)
        navCal = findViewById(R.id.nav_cal)
        recyclerView = findViewById(R.id.goalMainRecyclerView)
        gradeTagSpinner = findViewById(R.id.grade_tag)
    }

    private fun setupListeners() {
        // 클릭 리스너 설정
        navHome.setOnClickListener { navigateTo(HomeActivity::class.java) }
        navWrong.setOnClickListener { navigateTo(WrongAnswerListActivity::class.java) }
        navGoal.setOnClickListener { /* 현재 액티비티에 남아있도록*/ }
        navCal.setOnClickListener { navigateTo(Calendar::class.java) }

        // 스피너 리스너 설정
        gradeTagSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                // 선택된 항목의 인덱스 저장 및 과목 리스트 갱신
                selectedGradeIndex = position + 1
                fetchSubjectsByGrade(selectedGradeIndex)
                Log.d("grade", selectedGradeIndex.toString())
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 선택된 항목이 없을 때의 동작
            }
        }
    }

    fun fetchSubjectsByGrade(grade: Int) {

        apiService.getSubjectsByGrade(grade).enqueue(object : Callback<List<Subject>> {
            override fun onResponse(call: Call<List<Subject>>, response: Response<List<Subject>>) {
                if (response.isSuccessful) {
                    val subjectList = response.body()
                    subjectList?.let {
                        for (subject in it) {
//                            Log.d("Subject", "ID: ${subject.subjectId}, Name: ${subject.subjectName}, Image: ${subject.img}")
                        }
                    }
                } else {
                    Log.e("API Error", "Error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Subject>>, t: Throwable) {
                Log.e("Network Error", "Error: ${t.message}")
            }
        })
    }


    private fun updateRecyclerView(subjectList: List<Subject>) {
        // RecyclerView 업데이트
        val adapter = GoalSubjectTabAdapter { adapterOnClick(it) }
        recyclerView.adapter = adapter
        adapter.submitList(subjectList)
    }

    private fun adapterOnClick(subject: Subject) {
        // 과목 클릭 시 액티비티 이동
        navigateTo(GoalListActivity::class.java)
    }

    private fun navigateTo(destination: Class<*>) {
        // 다른 액티비티로 이동하는 함수
        val intent = Intent(this, destination)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        startActivity(intent)
    }

    private fun handleErrorResponse(responseCode: Int) {
        // 에러 응답 코드 처리
        when (responseCode) {
            // 특정한 에러 코드 처리 또는 일반적인 에러 메시지 표시
            else -> Log.e("APIError", "Error: $responseCode")
        }
    }
}