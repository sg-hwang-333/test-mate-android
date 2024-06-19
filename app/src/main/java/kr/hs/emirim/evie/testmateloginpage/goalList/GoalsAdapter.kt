package kr.hs.emirim.evie.testmateloginpage.goalList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textview.MaterialTextView
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.goalList.data.Goal
import kr.hs.emirim.evie.testmateloginpage.goalList.data.GoalResponse


class GoalsAdapter(private val onClick: (GoalResponse) -> Unit) :
    ListAdapter<GoalResponse, GoalsAdapter.GoalViewHolder>(GoalDiffCallback) {

    inner class GoalViewHolder(itemView: View, val onClick: (GoalResponse) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val goalEditText: EditText = itemView.findViewById(R.id.goal_description)
        val goalCheckBox: AppCompatCheckBox = itemView.findViewById(R.id.goal_checked)
        var currentGoal: GoalResponse? = null

        private lateinit var goalEditBtn: Button

        init {
            itemView.setOnClickListener {
                currentGoal?.let {
                    onClick(it)
                }
            }
        }

        /* UI에 정보 바인딩(넣는 메서드) */
        fun bind(goal: GoalResponse) {
            currentGoal = goal
            goalEditText.setText(goal.goal)
            goalCheckBox.isChecked = goal.completed
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.goal_layout, parent, false)

        return GoalViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        holder.bind(getItem(position)) // getItem(position) : 현재 아이템
    }

//    override fun getItemViewType(position: Int): Int {
//        val goal = getItem(position)
//        return if (goal.checked) {
//            1 // goalCheckBox가 true인 경우
//        } else {
//            0 // 그 외의 경우
//        }
//    }

}

object GoalDiffCallback : DiffUtil.ItemCallback<GoalResponse>() {
    override fun areItemsTheSame(oldItem: GoalResponse, newItem: GoalResponse): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: GoalResponse, newItem: GoalResponse): Boolean {
        return oldItem.goalId == newItem.goalId
    }
}