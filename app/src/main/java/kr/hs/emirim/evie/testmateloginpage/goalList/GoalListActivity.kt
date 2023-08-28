package kr.hs.emirim.evie.testmateloginpage.goalList

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.goalList.data.Goal

class GoalListActivity : AppCompatActivity() {

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

        goalsListViewModel.goalsLiveData.observe(this, {
            it?.let {
                goalsAdapter.submitList(it as MutableList<Goal>)
            }
        })

        val list = mutableListOf<Goal>()

        val modifyButton: Button = findViewById(R.id.buttonModify)
        modifyButton.setOnClickListener {
            list.add(Goal(8, "이름이름이름", true))
        }

        val listView = findViewById<RecyclerView>(R.id.goalRecyclerView)
        listView.setHasFixedSize(true)
//        listView.layoutManager =


    }

    private fun adapterOnClick(goal: Goal) {
        bottomSheetDialog.show()
    }

    private fun btnModifyOnClick() {
        val intent = Intent(this, AddGoalActivity::class.java)
        startActivityForResult(intent, newGoalActivityRequestCode)
    }

}