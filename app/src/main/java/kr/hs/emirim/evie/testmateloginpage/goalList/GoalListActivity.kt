package kr.hs.emirim.evie.testmateloginpage.goalList


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.calendar.Calendar
import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.WrongAnswerListActivity
import kr.hs.emirim.evie.testmateloginpage.goalList.data.Goal
import kr.hs.emirim.evie.testmateloginpage.home.HomeActivity
import kr.hs.emirim.evie.testmateloginpage.subject.GoalMainListActivity

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

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.goal_list_page)
        supportActionBar?.hide()

        var beforeBtn = findViewById<ImageView>(R.id.before)

        beforeBtn.setOnClickListener{
            onBackPressed();
        }

        bottomSheetView = layoutInflater.inflate(R.layout.goal_bottom_sheet, null)
        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetView)

        val goalsAdapter = GoalsAdapter { goal -> adapterOnClick(goal) }
        val recyclerView: RecyclerView = findViewById(R.id.goalRecyclerView)
        recyclerView.adapter = goalsAdapter

        // TODO : must not be null 에러 고치기, 탭 바 설정
//        var goalEditText : EditText = recyclerView.findViewById(R.id.goal_description)
//        var goalChecked : AppCompatCheckBox = recyclerView.findViewById(R.id.goal_checked)



//        goalEditBtn = bottomSheetView.findViewById<Button>(R.id.bsv_edit_btn)
//        goalEditBtn.setOnClickListener {
//            goalDescription.isFocusable = true
//            goalDescription.isFocusableInTouchMode = true
//            goalDescription.requestFocus()
//        }

        goalEditBtn = bottomSheetView.findViewById<Button>(R.id.bsv_edit_btn)
        goalDeleteBtn = bottomSheetView.findViewById<Button>(R.id.bsv_delete_btn)


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

    }

    private fun adapterOnClick(goal: Goal) {
        clickedGoal = goal

        val goalDescription = findViewById<EditText>(R.id.goal_description)

        // TODO : goal(현재 클릭된)에 focus가도록
        if(goal.description != null)bottomSheetView.findViewById<TextView>(R.id.bsv_title).setText(goal.description.toString())
        else bottomSheetView.findViewById<TextView>(R.id.bsv_title).setText("목표를 수정하세요")

        bottomSheetDialog.show()

        goalEditBtn.setOnClickListener {
            bottomSheetDialog.dismiss()
        }

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