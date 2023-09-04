package kr.hs.emirim.evie.testmateloginpage.goalList

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kr.hs.emirim.evie.testmateloginpage.R

import kr.hs.emirim.evie.testmateloginpage.goalList.data.Goal
import org.w3c.dom.Text

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

        bottomSheetView = layoutInflater.inflate(R.layout.goal_bottom_sheet, null)
        bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(bottomSheetView)


        val goalsAdapter = GoalsAdapter { goal -> adapterOnClick(goal) }
        val recyclerView: RecyclerView = findViewById(R.id.goalRecyclerView)
        recyclerView.adapter = goalsAdapter


//        goalEditBtn = bottomSheetView.findViewById<Button>(R.id.bsv_edit_btn)
//        goalEditBtn.setOnClickListener {
//            goalDescription.isFocusable = true
//            goalDescription.isFocusableInTouchMode = true
//            goalDescription.requestFocus()
//        }


        goalDeleteBtn = bottomSheetView.findViewById<Button>(R.id.bsv_delete_btn)
        goalDeleteBtn.setOnClickListener {
//            goalsAdapter.removeGoal()
//            goalsListViewModel.deleteGoal(goal)
            bottomSheetDialog.dismiss()
        }

//        val rootView: View = findViewById(android.R.id.content)
//        rootView.setOnTouchListener { _, _ ->
//            .clearFocus()
//            goalDescription.isFocusable = false
//            goalDescription.isFocusableInTouchMode = false
//            false // 터치 이벤트를 소비하지 않고 전달
//        }


        goalsListViewModel.goalsLiveData.observe( // observer : 어떤 이벤트가 일어난 순간, 이벤트를 관찰하던 관찰자들이 바로 반응하는 패턴
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


    }

    private fun adapterOnClick(goal: Goal) {
        clickedGoal = goal

        val goalDescription = findViewById<EditText>(R.id.goal_description)

        // TODO : goal(현재 클릭된)에 focus가도록
        if(goal.description != null)bottomSheetView.findViewById<TextView>(R.id.bsv_title).setText(goal.description.toString())
        else bottomSheetView.findViewById<TextView>(R.id.bsv_title).setText("목표를 수정하세요")



        bottomSheetDialog.show()
    }

    private fun btnModifyOnClick() {
        goalsListViewModel.insertGoal()
    }

}