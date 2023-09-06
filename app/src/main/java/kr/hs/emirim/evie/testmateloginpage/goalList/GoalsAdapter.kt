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
        }

        /* UI에 정보 바인딩(넣는 메서드) */
        fun bind(goal: Goal) {
            currentGoal = goal

            goalEditText.setText(goal.description)
            goalCheckBox.isChecked = goal.checked
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
//        val layoutResId = if (viewType == 1) {
//            R.layout.goal_layout_checked // goalCheckBox가 true인 경우에는 다른 레이아웃 사용
//        } else {
//            R.layout.goal_layout // 그 외에는 기존 레이아웃 사용
//        }

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.goal_layout, parent, false)

        return GoalViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = getItem(position)
        holder.bind(goal)
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

object GoalDiffCallback : DiffUtil.ItemCallback<Goal>() {
    override fun areItemsTheSame(oldItem: Goal, newItem: Goal): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Goal, newItem: Goal): Boolean {
        return oldItem.id == newItem.id
    }
}