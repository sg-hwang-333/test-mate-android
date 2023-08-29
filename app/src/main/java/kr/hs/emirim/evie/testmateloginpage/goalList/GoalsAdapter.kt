package kr.hs.emirim.evie.testmateloginpage.goalList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.goalList.data.Goal


class GoalsAdapter(private val onClick: (Goal) -> Unit) :
    ListAdapter<Goal, GoalsAdapter.GoalViewHolder>(GoalDiffCallback) {

    class GoalViewHolder(itemView: View, val onClick: (Goal) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val goalTextView: TextView = itemView.findViewById(R.id.goal_description)
        private val goalCheckBox: AppCompatCheckBox = itemView.findViewById(R.id.goal_checked)
        private var currentGoal: Goal? = null

        init {
            itemView.setOnClickListener {
                currentGoal?.let {
                    onClick(it)
                }
            }
        }

        /* Bind flower name and image. */
        fun bind(goal: Goal) {
            currentGoal = goal

            goalTextView.text = goal.description
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

//class MusicListAdapter(val data: List<Goal>) : RecyclerView.Adapter<MusicListAdapter.ItemViewHolder>() {
//    class ItemViewHolder(val view: View) : RecyclerView.ViewHolder(view) {}
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
//        val view = LayoutInflater
//            .from(parent.context)
//            .inflate(viewType, parent, false)
//        // viewType : Layout id
//        // parent : ViewGroup
//        return ItemViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
//        val item = data[position]
//        val textView = holder.view.findViewById<TextView>(R.id.goal_description)
//        textView.text = item.description
//    }
//
//    override fun getItemCount(): Int {
//        return data.size
//    }
//
//    override fun getItemViewType(position: Int): Int {
//        return R.layout.goal_layout
//    }
//}