package kr.hs.emirim.evie.testmateloginpage.goalList

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kr.hs.emirim.evie.testmateloginpage.R

import kr.hs.emirim.evie.testmateloginpage.goalList.data.Goal


class GoalsAdapter(private val onClick: (Goal) -> Unit) :
    ListAdapter<Goal, GoalsAdapter.GoalViewHolder>(GoalDiffCallback) {

    inner class GoalViewHolder(itemView: View, val onClick: (Goal) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val goalEditText: EditText = itemView.findViewById(R.id.goal_description)
        val goalCheckBox: AppCompatCheckBox = itemView.findViewById(R.id.goal_checked)
        var currentGoal: Goal? = null

        private lateinit var goalEditBtn: Button

        init {
            itemView.setOnClickListener {
                currentGoal?.let {
                    onClick(it)
                }
            }

//            goalEditText.setOnKeyListener(View.OnKeyListener { v, keyCode, event -> //Enter key Action
//                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
//                    //키패드 내리기
//                    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
//                    imm.hideSoftInputFromWindow(goalEditText.windowToken, 0)
//
//                    //처리
//                    return@OnKeyListener true
//                }
//                false
//            })



        }

        /* Bind flower name and image. */
        fun bind(goal: Goal) {
            currentGoal = goal

            goalEditText.setText(goal.description)
            goalCheckBox.isChecked = goal.checked
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.goal_layout, parent, false)
        return GoalViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = getItem(position)
        holder.bind(goal)
    }
}

object GoalDiffCallback : DiffUtil.ItemCallback<Goal>() {
    override fun areItemsTheSame(oldItem: Goal, newItem: Goal): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Goal, newItem: Goal): Boolean {
        return oldItem.id == newItem.id
    }
}