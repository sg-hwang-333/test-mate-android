    package kr.hs.emirim.evie.testmateloginpage.home

    import android.app.Activity
    import android.content.Intent
    import android.os.Bundle
    import android.util.Log
    import android.view.View
    import android.widget.AdapterView
    import android.widget.HorizontalScrollView
    import android.widget.ImageButton
    import android.widget.Spinner
    import android.widget.TextView
    import android.widget.Toast
    import androidx.activity.viewModels
    import androidx.appcompat.app.AppCompatActivity
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.github.mikephil.charting.charts.LineChart
    import kr.hs.emirim.evie.testmateloginpage.R
    import kr.hs.emirim.evie.testmateloginpage.api.home.HomeAPIService
    import kr.hs.emirim.evie.testmateloginpage.calendar.Calendar
    import kr.hs.emirim.evie.testmateloginpage.comm.RetrofitClient
    import kr.hs.emirim.evie.testmateloginpage.databinding.ActivityHomeBinding
    import kr.hs.emirim.evie.testmateloginpage.home.data.HomeSubjectInfoResponse
    import kr.hs.emirim.evie.testmateloginpage.home.data.TestData
    import kr.hs.emirim.evie.testmateloginpage.login.CurrentUser
    import kr.hs.emirim.evie.testmateloginpage.subject.AddSubjectActivity
    import kr.hs.emirim.evie.testmateloginpage.subject.GoalMainListActivity
    import kr.hs.emirim.evie.testmateloginpage.subject.SubjectHomeAdapter
    import kr.hs.emirim.evie.testmateloginpage.subject.SubjectViewModel
    import kr.hs.emirim.evie.testmateloginpage.subject.SubjectViewModelFactory
    import kr.hs.emirim.evie.testmateloginpage.subject.data.SubjectResponse
    import kr.hs.emirim.evie.testmateloginpage.util.SpinnerUtil
    import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.WrongAnswerListActivity
    import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.WrongAnswerListViewModel
    import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.WrongAnswerListViewModelFactory
    import retrofit2.Call
    import retrofit2.Callback
    import retrofit2.Response

    class HomeActivity : AppCompatActivity() {
        // 시험기록 데이터 생성
        val testRecordDataList: List<TestData> = listOf(
            TestData("1학년 2학기 기말", 75),
            TestData("1학년 2학기 중간", 60),
            TestData("1학년 1학기 기말", 80),
            TestData("1학년 1학기 중간", 100),
            TestData("@학년 @학기 중간", 89),
            TestData("@학년 @학기 중간", 91),
            TestData("@학년 @학기 중간", 78),
            TestData("@학년 @학기 중간", 96)
        )

//        private lateinit var subjectIdTextView: TextView
        private lateinit var dateTextView: TextView
//        private lateinit var levelTextView: TextView
//        private lateinit var goalScoreTextView: TextView
//        private lateinit var failTextView: TextView
//        private lateinit var examsRecyclerView: RecyclerView

        lateinit var navHome: ImageButton
        lateinit var navGoal: ImageButton
        lateinit var navCal: ImageButton
        lateinit var navWrong: ImageButton
        lateinit var addSubjectBtn: ImageButton
        lateinit var editTestRecordBtn: ImageButton
        lateinit var userGrade: TextView
        lateinit var spinner: Spinner
        lateinit var toggle: ImageButton
        lateinit var subjectsAdapter : SubjectHomeAdapter

        private val newSubjectActivityRequestCode = 1

        private val subjectsListViewModel by viewModels<SubjectViewModel> {
            SubjectViewModelFactory(this)
        }

        private val listViewModel by viewModels<WrongAnswerListViewModel> {
            WrongAnswerListViewModelFactory(this)
        }

        var selectedPosition : Int? = null

        private lateinit var binding: ActivityHomeBinding

        // Retrofit 서비스 인스턴스
        private lateinit var homeAPIService: HomeAPIService

        // onCreate 메서드는 액티비티가 처음 생성될 때 호출
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            val binding = ActivityHomeBinding.inflate(layoutInflater)
            setContentView(binding.root)
            setContentView(R.layout.activity_home)
            supportActionBar?.hide()

            // findViewById를 사용하여 레이아웃 파일에서 뷰를 가져와 변수에 할당
//            subjectIdTextView = findViewById(R.id.subjectIdTextView)
            dateTextView = findViewById(R.id.dday)
//            levelTextView = findViewById(R.id.level)
//            goalScoreTextView = findViewById(R.id.goal_score)
//            failTextView = findViewById(R.id.fail)
//            examsRecyclerView = findViewById(R.id.examsRecyclerView)

            // Context를 사용하여 RetrofitClient를 생성

            // RetrofitClient를 사용하여 homeAPIService 초기화
            homeAPIService = RetrofitClient.create(HomeAPIService::class.java, this)

            val subjectId = 1 // TODO : 실제로는 이 값을 동적으로 설정해야 함 -> 스피너에 있는 subjectId
            fetchSubjectData(subjectId)

            // 성적 그래프
            val linechart = findViewById<LineChart>(R.id.home_test_record_chart)
            val horizontalScrollView = findViewById<HorizontalScrollView>(R.id.home_scroll_view_graph)
            val scoreChart = ScoreChart(linechart, horizontalScrollView, this)
            scoreChart.setupChart(testRecordDataList)

    //       toggle = findViewById(R.id.toggle)
    //       drawerLayout = findViewById(R.id.drawer_layout)
    //       toggle.setOnClickListener {
    //           drawerLayout.openDrawer(GravityCompat.START)
    //
    //       }

            Log.d("homeLog", CurrentUser.selectGrade.toString())

            spinner = SpinnerUtil.gradeSpinner(this, R.id.spinnerWrong)
            spinner.setSelection(CurrentUser.selectGrade!! - 1)
            selectedPosition = spinner.selectedItemPosition// grade 인덱스 (ex. 3)
            var selectedItem =
                spinner.getItemAtPosition(selectedPosition!!).toString() // grade 문자열 (ex. 고등학교 2학년)
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    // 선택된 항목의 위치(position)를 이용하여 해당 항목의 값을 가져옴
                    selectedPosition = position + 1
                    CurrentUser.selectGrade = spinner.selectedItemPosition + 1
                    subjectsListViewModel.readSubjectList(selectedPosition!!)

                }

                override fun onNothingSelected(parent: AdapterView<*>?) {
                    // 아무 것도 선택되지 않았을 때 처리할 작업
                }
            }

            // subjectRecyclerView
            subjectsAdapter = SubjectHomeAdapter { subject -> adapterOnClick(subject) } // TODO
            val recyclerView: RecyclerView = findViewById(R.id.subjectRecyclerView)

//            binding.subjectRecyclerView.apply {
//                layoutManager = LinearLayoutManager(this@HomeActivity, LinearLayoutManager.HORIZONTAL, false)
//                adapter = subjectsAdapter
//            }
            recyclerView.layoutManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) // 수평 레이아웃 방향 설정
            recyclerView.adapter = subjectsAdapter

            subjectsListViewModel.subjectListData.observe(
                // observer : 어떤 이벤트가 일어난 순간, 이벤트를 관찰하던 관찰자들이 바로 반응하는 패턴
                this
            ) { map ->
                map?.let {
                    val subjectsForSelectedGrade = it[selectedPosition]
                    subjectsForSelectedGrade?.let { subjects ->
                        subjectsAdapter.submitList(subjects as MutableList<SubjectResponse>) // 어댑터 내의 데이터를 새 리스트로 업데이트하는 데 사용
                    }
                }
            }

            // 과목 추가
            addSubjectBtn = findViewById(R.id.addSubjectBtn)
            addSubjectBtn.setOnClickListener {
                Log.d("homeLog", "addSubjectBtn 클릭!")
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
                val intent = Intent(this@HomeActivity, EditTestRecordActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
    //            startActivity(intent)
                startActivityForResult(intent, newSubjectActivityRequestCode)
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
        } // onCreate

        private fun fetchSubjectData(subjectId: Int) {
            try {
                val call = homeAPIService.getHomeSubjectInfo(subjectId)
                Log.d("fetchSubjectData", "Fetching data for subjectId: $subjectId")

                call.enqueue(object : Callback<HomeSubjectInfoResponse> {
                    override fun onResponse(
                        call: Call<HomeSubjectInfoResponse>,
                        response: Response<HomeSubjectInfoResponse>
                    ) {
                        Log.d("fetchSubjectData", "API call successful, Response code: ${response.code()}")
                        if (response.isSuccessful) {
                            val subjectResponse = response.body()
                            Log.i("fetchSubjectData", "Response body: $subjectResponse")
                            subjectResponse?.let {
                                // UI 업데이트
                                dateTextView.text = it.date
//                                levelRatingBar.rating = it.level.toFloat() // RatingBar는 float 타입으로 설정
//                                goalScoreTextView.text = "목표점수 ${it.goalScore}점"

                            }
                        } else {
                            Log.e("fetchSubjectData", "Failed to get subject data. Error code: ${response.code()}")
                            Toast.makeText(this@HomeActivity, "과목 정보를 불러오는데 실패했습니다!", Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onFailure(call: Call<HomeSubjectInfoResponse>, t: Throwable) {
                        Log.e("fetchSubjectData", "API call failed: ${t.message}", t)
                        Toast.makeText(this@HomeActivity, t.message, Toast.LENGTH_SHORT).show()
                    }
                })
            } catch (e: Exception) {
                Log.e("fetchSubjectData", "Exception during API call", e)
            }
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
            super.onActivityResult(requestCode, resultCode, intentData)

            if (requestCode == newSubjectActivityRequestCode && resultCode == Activity.RESULT_OK) {
                // AddSubjectActivity가 성공적으로 종료되었을 때
                // Subject List를 업데이트
                selectedPosition?.let {
                    Log.d("homeRestart", "실행요미")
                    subjectsListViewModel.readSubjectList(it)
                }
            } else {
                Log.d("homeRestart", "Request code or Result code mismatch")
            }
        }

//        override fun onRestart() {
//            super.onRestart()
//
//            selectedPosition?.let { subjectsListViewModel.readSubjectList(it) }
//            subjectsListViewModel.subjectListData.observe(
//            // observer : 어떤 이벤트가 일어난 순간, 이벤트를 관찰하던 관찰자들이 바로 반응하는 패턴
//        this
//            ) { map ->
//            map?.let {
//                val subjectsForSelectedGrade = it[CurrentUser.selectGrade]
//                subjectsForSelectedGrade?.let { subjects ->
//                subjectsAdapter.submitList(subjects as MutableList<SubjectResponse>)
//                    Log.d("homeRestart", "실행요미")}
//            } }
//        }

        fun adapterOnClick(subject: SubjectResponse) {
            // TODO : 과목별 화면으로 이동
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
    //                val newSubject = Subject(
    //                    CurrentUser.userDetails!!.grade, subjectName, subjectImage
    //                )
    //
    //                subjectsListViewModel.addList(newSubject) ///////////////////////////////////////// insertFlower
    //            }
    //        }
    //    }
    }