package kr.hs.emirim.evie.testmateloginpage.goalList

import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
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
import kr.hs.emirim.evie.testmateloginpage.goalList.data.GoalPatchRequest
import kr.hs.emirim.evie.testmateloginpage.goalList.data.GoalResponse
import kr.hs.emirim.evie.testmateloginpage.goalList.data.toGoalPatchRequest


class GoalsAdapter(private val onClick: (GoalResponse) -> Unit, private val onUpdate: (GoalPatchRequest) -> Unit) :
    ListAdapter<GoalResponse, GoalsAdapter.GoalViewHolder>(GoalDiffCallback) {

    inner class GoalViewHolder(itemView: View, val onClick: (GoalResponse) -> Unit, val onUpdate: (GoalPatchRequest) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val goalEditText: EditText = itemView.findViewById(R.id.goal_description)
        val goalCheckBox: AppCompatCheckBox = itemView.findViewById(R.id.goal_checked)
        var currentGoal: GoalResponse? = null

        private lateinit var goalEditBtn: Button

        private val handler = Handler(Looper.getMainLooper())
        private var lastTextEditTime: Long = 0

        private val saveGoalRunnable = Runnable {
            currentGoal?.let {
                val goalPatchRequest = it.toGoalPatchRequest()
                if (System.currentTimeMillis() >= lastTextEditTime + 2000) {
                    it.goal = goalEditText.text.toString()
                    onUpdate(goalPatchRequest)
                }
            }
        }

        init {
            itemView.setOnClickListener {
                currentGoal?.let {
                    onClick(it)
                }
            }

            goalEditText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    handler.removeCallbacks(saveGoalRunnable)
                }

                override fun afterTextChanged(s: Editable?) {
                    lastTextEditTime = System.currentTimeMillis()
                    handler.postDelayed(saveGoalRunnable, 2000) // 2초 후에 실행
                }
            })
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

        return GoalViewHolder(view, onClick, onUpdate)
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