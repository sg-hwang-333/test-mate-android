package kr.hs.emirim.evie.testmateloginpage.goalmain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject


class GoalSubjectTabAdapter(private val onClick: (Subject) -> Unit) :
    ListAdapter<Subject, GoalSubjectTabAdapter.GoalSubjectTabHolder>(GoalSubjectDiffCallback) {

    inner class GoalSubjectTabHolder(itemView: View, val onClick: (Subject) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val subjectTextView: TextView = itemView.findViewById(R.id.subjectName)
        var currentSubject: Subject? = null

        init {
            itemView.setOnClickListener {
                currentSubject?.let {
                    onClick(it)
                }
            }
        }

        /* UI에 정보 바인딩(넣는 메서드) */
        fun bind(subject: Subject) {
            currentSubject = subject

            subjectTextView.text = subject.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalSubjectTabHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.goal_main_layout, parent, false)

        return GoalSubjectTabHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: GoalSubjectTabHolder, position: Int) {
        val subject = getItem(position)
        holder.bind(subject)
    }

}

object GoalSubjectDiffCallback : DiffUtil.ItemCallback<Subject>() {
    override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean {
        return oldItem.id == newItem.id
    }
}