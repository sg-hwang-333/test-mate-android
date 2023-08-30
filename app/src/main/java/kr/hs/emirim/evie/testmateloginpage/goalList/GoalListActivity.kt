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

    lateinit var goalEditBtn : android.widget.Button
    lateinit var goalDeleteBtn : android.widget.Button

    private lateinit var bottomSheetView: View
    private lateinit var bottomSheetDialog: BottomSheetDialog

    private lateinit var btnModify : android.widget.Button

    private val newGoalActivityRequestCode = 1
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

        goalEditBtn = bottomSheetView.findViewById<Button>(R.id.bsv_edit_btn)
        goalEditBtn.setOnClickListener {
            findViewById<EditText>(R.id.goal_description).requestFocus()
            findViewById<EditText>(R.id.goal_description).setFocusable(true);
            // TODO : 객체의 edittext로 Focus이동
        }

        goalDeleteBtn = bottomSheetView.findViewById<Button>(R.id.bsv_delete_btn)
        goalDeleteBtn.setOnClickListener {
            goalsAdapter.removeGoal()
        }


        goalsListViewModel.goalsLiveData.observe(this, {
            it?.let {
                goalsAdapter.submitList(it as MutableList<Goal>)
            }
        })

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
        bottomSheetView.findViewById<TextView>(R.id.bsv_title).setText(goal.description.toString())
        bottomSheetDialog.show()
    }

    private fun btnModifyOnClick() {
        goalsListViewModel.insertGoal()
    }

}