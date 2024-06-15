package kr.hs.emirim.evie.testmateloginpage.home

import android.annotation.SuppressLint
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.navigation.findNavController
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.api.home.HomeAPIService
import kr.hs.emirim.evie.testmateloginpage.comm.RetrofitClient
import kr.hs.emirim.evie.testmateloginpage.home.data.HomeSubjectInfoResponse
import kr.hs.emirim.evie.testmateloginpage.home.data.TestData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class EditTestRecordActivity : AppCompatActivity() {
    // 시험기록 데이터 생성 : 홈 과목 정보 안에 있는 Exam으로 설정
    var testRecordDataList: MutableList<HomeSubjectInfoResponse.Exam> = mutableListOf()

    // Retrofit 서비스 인스턴스
    private lateinit var homeAPIService: HomeAPIService
    private lateinit var testNameEditText: EditText
    private lateinit var testScoreEditText: EditText
    private lateinit var targetScore: TextView
    private lateinit var beforeBtn: ImageView
    private lateinit var BtnSetTestDate: Button
    private lateinit var targetScoreSeekbar: SeekBar
    private lateinit var BtnSave: Button
    private lateinit var RatingBarTestDifficulty: RatingBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_test_record)

        // RetrofitClient를 사용하여 homeAPIService 초기화
        homeAPIService = RetrofitClient.create(HomeAPIService::class.java, this)

        /// findViewById를 사용하여 레이아웃 파일에서 뷰를 가져와 변수에 할당
        testNameEditText = findViewById(R.id.test_name)
        testScoreEditText = findViewById(R.id.test_score)
        targetScore = findViewById<TextView>(R.id.target_score) // 목표점수
        beforeBtn = findViewById<ImageView>(R.id.backBtn) // 전으로 돌아가기 버튼
        BtnSetTestDate = findViewById<Button>(R.id.btn_test_date) // 시험 날짜 설정 버튼
        targetScoreSeekbar = findViewById<SeekBar>(R.id.target_score_seekbar) // 시험 점수 bar
        BtnSave = findViewById<Button>(R.id.buttonSave)  // 저장 버튼
        RatingBarTestDifficulty = findViewById<RatingBar>(R.id.ratingTestDifficulty) // 시험 난이도 RatingBar

        // 과목 정보 불러오기
        fetchSubjectData(1)

        // 이전 페이지 버튼 클릭 리스너
        beforeBtn.setOnClickListener{
            onBackPressed();
        }

        // 시험 날짜 버튼 클릭 리스너
        BtnSetTestDate.setOnClickListener {
            val newFragment: DialogFragment = DatePickerFragment()
            newFragment.show(supportFragmentManager, "datePicker")
        }




        targetScoreSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                // SeekBar의 값이 변경될 때 호출되는 메서드
                targetScore.text = "$progress 점"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // 사용자가 SeekBar를 터치할 때 호출되는 메서드
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // 사용자가 SeekBar를 터치를 끝낼 때 호출되는 메서드
            }
        })

        // 난이도 ratingBar
        RatingBarTestDifficulty.setOnRatingBarChangeListener { ratingBar, rating, fromUser ->
            // 등급을 로그에 출력
            Log.d("Rating", "Current rating: $rating")
        }

        BtnSave.setOnClickListener {

        }
    }

    // 홈 -> 과목 정보(시험 점수 리스트, 시험날짜, 난이도, 점수, 실패요소) 불러오기
    private fun fetchSubjectData(subjectId: Int) {
        try {
            val call = homeAPIService.getHomeSubjectInfo(subjectId)
            Log.d("fetchSubjectData", "Fetching data for subjectId: $subjectId")

            call.enqueue(object : Callback<HomeSubjectInfoResponse> {
                override fun onResponse(
                    call: Call<HomeSubjectInfoResponse>,
                    response: Response<HomeSubjectInfoResponse>
                ) {
                    Log.d(
                        "fetchSubjectData",
                        "API call successful, Response code: ${response.code()}"
                    )
                    if (response.isSuccessful) {
                        val subjectResponse = response.body()
                        Log.i("fetchSubjectData", "Response body: $subjectResponse")
                        subjectResponse?.let {
                            Log.e("시험기록", subjectResponse.toString())
                            HomeSubjectInfoupdateUI(subjectResponse)
                        }
                    } else {
                        Log.e(
                            "fetchSubjectData",
                            "Failed to get subject data. Error code: ${response.code()}"
                        )
                        Toast.makeText(
                            this@EditTestRecordActivity,
                            "과목 정보를 불러오는데 실패했습니다!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<HomeSubjectInfoResponse>, t: Throwable) {
                    Log.e("fetchSubjectData", "API call failed: ${t.message}", t)
                    Toast.makeText(this@EditTestRecordActivity, t.message, Toast.LENGTH_SHORT).show()
                }
            })
        } catch (e: Exception) {
            Log.e("fetchSubjectData", "Exception during API call", e)
        }
    }

    // 홈 -> 시험 과목 정보 부분 UI 업데이트
    @SuppressLint("ResourceType")
    private fun HomeSubjectInfoupdateUI(subjectResponse: HomeSubjectInfoResponse?) {
        subjectResponse?.let {
            targetScore.text = "${it.goalScore}점"
            targetScoreSeekbar.progress = it.goalScore
            RatingBarTestDifficulty.rating = it.level.toFloat()
            BtnSetTestDate.text = it.date

            testRecordDataList.clear() // 기존 데이터를 지우고 새로운 데이터로 업데이트
            testRecordDataList.addAll(it.exams) // testRecordDataList에 exams 정보 넣기

            // 성적 그래프
            val linechart = findViewById<LineChart>(R.id.test_record_chart)
            val horizontalScrollView = findViewById<HorizontalScrollView>(R.id.scroll_view_graph)
            val scoreChart = ScoreChart(linechart, horizontalScrollView, this) // MPAndroidChart 커스텀 클래스
            scoreChart.setupChart(testRecordDataList)

            // TODO : 시험 점수 리스트 추가하기

        }
    }

    fun processDatePickerResult(year: Int, month: Int, day: Int) {
        val monthString = (month + 1).toString()
        val dayString = day.toString()
        val yearString = year.toString()
        val selectedDate = "$yearString-$monthString-$dayString"

        // 선택한 날짜를 버튼에 설정
        BtnSetTestDate.text = selectedDate

        // Toast 메시지 대신 버튼 텍스트에 선택한 날짜가 표시되도록 설정
         Toast.makeText(this, "Date: $selectedDate", Toast.LENGTH_SHORT).show()
    }

}