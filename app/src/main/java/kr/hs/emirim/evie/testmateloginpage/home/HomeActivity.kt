package kr.hs.emirim.evie.testmateloginpage.home

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.HorizontalScrollView
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Calendar
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.DefaultValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import kr.hs.emirim.evie.testmateloginpage.subject.GoalMainListActivity
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.Wrong_answer_note
import kr.hs.emirim.evie.testmateloginpage.databinding.ActivityCalendarBinding
import kr.hs.emirim.evie.testmateloginpage.databinding.ActivityHomeBinding
import kr.hs.emirim.evie.testmateloginpage.home.data.TestData
import kr.hs.emirim.evie.testmateloginpage.subject.GoalMainSubjectsViewModel
import kr.hs.emirim.evie.testmateloginpage.subject.GoalMainViewModelFactory
import kr.hs.emirim.evie.testmateloginpage.subject.AddSubjectActivity
import kr.hs.emirim.evie.testmateloginpage.subject.SUBJECT_NAME
import kr.hs.emirim.evie.testmateloginpage.subject.SubjectHomeAdapter
import kr.hs.emirim.evie.testmateloginpage.subject.SubjectsListViewModel
import kr.hs.emirim.evie.testmateloginpage.subject.SubjectsListViewModelFactory
import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject

class HomeActivity : AppCompatActivity() {
    // 시험기록 데이터 생성
    val testRecordDataList: List<TestData> = listOf(
        TestData("1학년 2학기 기말",75),
        TestData("1학년 2학기 중간",60),
        TestData("1학년 1학기 기말",80),
        TestData("1학년 1학기 중간",100),
        TestData("@학년 @학기 중간",89),
        TestData("@학년 @학기 중간",91),
        TestData("@학년 @학기 중간",78),
        TestData("@학년 @학기 중간",96)
    )

    lateinit var navHome: ImageButton
    lateinit var navGoal: ImageButton
    lateinit var navCal: ImageButton

    lateinit var navWrong: ImageButton

    lateinit var addSubjectBtn: ImageButton
    lateinit var editTestRecordBtn: ImageButton

    lateinit var userGrade: TextView
    lateinit var spinner: Spinner

    lateinit var toggle: ImageButton

    private val newSubjectActivityRequestCode = 1
    private val subjectsListViewModel by viewModels<SubjectsListViewModel> {
        SubjectsListViewModelFactory(this)
    }

    private val goalMainSubjectsViewModel by viewModels<GoalMainSubjectsViewModel> {
        GoalMainViewModelFactory(this)
    }

    private lateinit var binding: ActivityHomeBinding
    private lateinit var drawerLayout: DrawerLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 성적 그래프
        val linechart = findViewById<LineChart>(R.id.test_record_chart)
        val xAxis = linechart.xAxis

        val entries: MutableList<Entry> = mutableListOf() // Entry : 데이터 포인트를 나타내는 클래스
        for (i in testRecordDataList.indices){
            entries.add(Entry(i.toFloat(), testRecordDataList[i].score.toFloat()))
        }
        val lineDataSet = LineDataSet(entries,"entries")

        lineDataSet.apply {
            color = resources.getColor(R.color.green_500, null) // 선 색깔
            circleRadius = 5f
            lineWidth = 3f
            setCircleColor(resources.getColor(R.color.green_500, null))
            circleHoleColor = resources.getColor(R.color.green_500, null)
            setDrawHighlightIndicators(false)
            setDrawValues(true) // 숫자표시
            valueTextColor = resources.getColor(R.color.black, null)
            valueFormatter = DefaultValueFormatter(0)  // 소숫점 자릿수 설정
            valueTextSize = 10f
        }

        //차트 전체 설정
        linechart.apply {
            axisRight.isEnabled = true   //y축 사용여부
            axisLeft.isEnabled = true
            legend.isEnabled = false    //legend 사용여부
            description.isEnabled = false //주석
            isDragXEnabled = true   // x 축 드래그 여부
            isScaleYEnabled = false //y축 줌 사용여부
            isScaleXEnabled = false //x축 줌 사용여부
        }

        //X축 설정
        xAxis.apply {
            setDrawGridLines(false)
            setDrawAxisLine(true)
            setDrawLabels(true)
            position = XAxis.XAxisPosition.BOTTOM
            valueFormatter =
                EditTestRecordActivity.XAxisCustomFormatter(changeTestDateText(testRecordDataList))
            textColor = resources.getColor(R.color.black, null)
            textSize = 10f
            labelRotationAngle = 0f
            setLabelCount(10, true)
        }

        val horizontalScrollView = findViewById<HorizontalScrollView>(R.id.scroll_view_graph)
        horizontalScrollView.post{
            horizontalScrollView.scrollTo(
                linechart.width,
                0
            )
        }

        linechart.apply {
            data = LineData(lineDataSet)
            notifyDataSetChanged() //데이터 갱신
            invalidate() // view갱신
        }
        // 성적 그래프

        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

//       toggle = findViewById(R.id.toggle)
//       drawerLayout = findViewById(R.id.drawer_layout)
//       toggle.setOnClickListener {
//           drawerLayout.openDrawer(GravityCompat.START)
//
//       }

        val pre = getSharedPreferences("UserInfo", MODE_PRIVATE)
        val grade = pre.getString("usergrade", "고등학교 2학년 ") // 기본값 설정

        val facilityList = arrayOf("선택하세요")

//        spinner = findViewById(R.id.spinner)

        val facilityListWithUserGrade = mutableListOf(*facilityList, grade)
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            facilityListWithUserGrade
        )
// 스피너에 어댑터 설정
        spinner.adapter = adapter


        userGrade = findViewById<TextView>(R.id.userGrade)
        userGrade.text = grade + "국어"

        addSubjectBtn = findViewById(R.id.addSubjectBtn)
        val subjectsAdapter = SubjectHomeAdapter { subject -> adapterOnClick(subject) } // TODO
        val recyclerView: RecyclerView = findViewById(R.id.subjectRecyclerView)

        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) // 수평 레이아웃 방향 설정
        recyclerView.adapter = subjectsAdapter

        subjectsListViewModel.subjectsLiveData.observe(
            // observer : 어떤 이벤트가 일어난 순간, 이벤트를 관찰하던 관찰자들이 바로 반응하는 패턴
            this
        ) {
            it?.let { // goalsLiveData의 값이 null이 아닐 때 중괄호 코드 실행
                subjectsAdapter.submitList(it as MutableList<Subject>) // 어댑터 내의 데이터를 새 리스트로 업데이트하는 데 사용
            }
        }

        addSubjectBtn.setOnClickListener {
            val intent = Intent(this@HomeActivity, AddSubjectActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
//            startActivity(intent)
            startActivityForResult(intent, newSubjectActivityRequestCode)
        }
//       binding.addSubjectBtn.setOnClickListener {
//           val dialog = AddSubjectActivity()
//           dialog.show(supportFragmentManager, "CustomDialog")
//       }

        editTestRecordBtn = findViewById(R.id.edit_test_record_btn)
        editTestRecordBtn.setOnClickListener {

        }


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
            val intent = Intent(this, Wrong_answer_note::class.java)
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
    } // onCreate

    // 성적 그래프
    fun changeTestDateText(dataList: List<TestData>): List<String> {
        val dataTextList = ArrayList<String>()
        for (i in dataList.indices) {
            dataTextList.add(dataList[i].testDate)
        }
        return dataTextList
    }

    class XAxisCustomFormatter(val xAxisData: List<String>) : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return xAxisData[(value).toInt()]
        }

    }
    // 성적 그래프


    private fun adapterOnClick(subject: Subject) {
        // TODO : 과목별 화면으로 이동
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        /* Inserts flower into viewModel. */
        if (resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val subjectName = data.getStringExtra(SUBJECT_NAME)

                subjectsListViewModel.insertSubject(subjectName) ///////////////////////////////////////// insertFlower
                goalMainSubjectsViewModel.insertSubject(subjectName)
            }
        }
    }
}