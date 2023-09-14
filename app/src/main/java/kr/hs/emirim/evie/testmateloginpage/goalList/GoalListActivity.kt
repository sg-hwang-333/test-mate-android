package kr.hs.emirim.evie.testmateloginpage.goalList

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Calendar
import com.google.android.material.bottomsheet.BottomSheetDialog
import kr.hs.emirim.evie.testmateloginpage.GoalMainListActivity
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.goalList.data.Goal

class GoalListActivity : AppCompatActivity() {

    private lateinit var bottomSheetView: View
    private lateinit var bottomSheetDialog: BottomSheetDialog
    lateinit var goalEditBtn : android.widget.Button
    lateinit var goalDeleteBtn : android.widget.Button

    private lateinit var btnAddGoal : android.widget.Button

    private var clickedGoal: Goal? = null

    private val goalsListViewModel by viewModels<GoalsListViewModel> {
        GoalsListViewModelFactory(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goal_list_page)
        supportActionBar?.hide()

        //        Handler().postDelayed(Runnable { // 스플래시 화면 종료 후 가이드 페이지로 이동
//            val intent = Intent(this@MainActivity, GoalMainActivity::class.java)
//            startActivity(intent)
//            finish()
//        }, 3000)

        bottomSheetView = layoutInflater.inflate(R.layout.goal_bottom_sheet, null)
        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetView)

        val goalsAdapter = GoalsAdapter { goal -> adapterOnClick(goal) }
        val recyclerView: RecyclerView = findViewById(R.id.goalRecyclerView)
        recyclerView.adapter = goalsAdapter

        // TODO : must not be null 에러 고치기, 탭 바 설정
//        var goalEditText : EditText = recyclerView.findViewById(R.id.goal_description)
//        var goalChecked : AppCompatCheckBox = recyclerView.findViewById(R.id.goal_checked)


//        goalEditText!!.setOnKeyListener(View.OnKeyListener { v, keyCode, event -> //Enter key Action
//            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
//                //키패드 내리기
//                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//                imm.hideSoftInputFromWindow(goalEditText!!.getWindowToken(), 0)
//
//                //처리
//                return@OnKeyListener true
//            }
//            false
//        })


//        goalEditBtn = bottomSheetView.findViewById<Button>(R.id.bsv_edit_btn)
//        goalEditBtn.setOnClickListener {
//            goalDescription.isFocusable = true
//            goalDescription.isFocusableInTouchMode = true
//            goalDescription.requestFocus()
//        }


        goalDeleteBtn = bottomSheetView.findViewById<Button>(R.id.bsv_delete_btn)


//        val rootView: View = findViewById(android.R.id.content)
//        rootView.setOnTouchListener { _, _ ->
//            .clearFocus()
//            goalDescription.isFocusable = false
//            goalDescription.isFocusableInTouchMode = false
//            false // 터치 이벤트를 소비하지 않고 전달
//        }


        goalsListViewModel.goalsLiveData.observe(
            // observer : 어떤 이벤트가 일어난 순간, 이벤트를 관찰하던 관찰자들이 바로 반응하는 패턴
            this,
        ) {
            it?.let { // goalsLiveData의 값이 null이 아닐 때 중괄호 코드 실행
                goalsAdapter.submitList(it as MutableList<Goal>) // 어댑터 내의 데이터를 새 리스트로 업데이트하는 데 사용
            }
        }

//        val list = mutableListOf<Goal>()

        val modifyButton: Button = findViewById(R.id.buttonModify)
        modifyButton.setOnClickListener {
            btnModifyOnClick()
        }

        val listView = findViewById<RecyclerView>(R.id.goalRecyclerView)
        listView.setHasFixedSize(true)
//        listView.layoutManager =

        // navgation
        var navHome : ImageButton = findViewById(R.id.nav_home)
        var navWrong : ImageButton = findViewById(R.id.nav_wrong)
        var navGoal : ImageButton = findViewById(R.id.nav_goal)
        var navCal : ImageButton = findViewById(R.id.nav_cal)

        navHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }
        navWrong.setOnClickListener {
//            val intent = Intent(this, Wrong_answer_note::class.java)
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

    private fun adapterOnClick(goal: Goal) {
        clickedGoal = goal

        val goalDescription = findViewById<EditText>(R.id.goal_description)

        // TODO : goal(현재 클릭된)에 focus가도록
        if(goal.description != null)bottomSheetView.findViewById<TextView>(R.id.bsv_title).setText(goal.description.toString())
        else bottomSheetView.findViewById<TextView>(R.id.bsv_title).setText("목표를 수정하세요")

        bottomSheetDialog.show()

        goalDeleteBtn.setOnClickListener {
//            goalsAdapter.removeGoal()
            goalsListViewModel.removeGoal(goal)
            bottomSheetDialog.dismiss()
        }
    }

    private fun btnModifyOnClick() {
        goalsListViewModel.insertGoal()
    }

}