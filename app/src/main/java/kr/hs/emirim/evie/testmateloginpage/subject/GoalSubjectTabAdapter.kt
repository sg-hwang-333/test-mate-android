package kr.hs.emirim.evie.testmateloginpage.subject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject
import kr.hs.emirim.evie.testmateloginpage.util.ImgResourceStringToInt


class GoalSubjectTabAdapter(private val onClick: (Subject, Int) -> Unit) :
    ListAdapter<Subject, GoalSubjectTabAdapter.GoalSubjectTabHolder>(GoalSubjectDiffCallback) {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class GoalSubjectTabHolder(itemView: View, val onClick: (Subject, Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val subjectTextView: TextView = itemView.findViewById(R.id.subjectName)
        val subjectImageView: ImageView = itemView.findViewById(R.id.subjectImage)

        var currentSubject: Subject? = null

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onClick(getItem(position), position)
                }
            }
        }

        /* UI에 정보 바인딩(넣는 메서드) */
        fun bind(subject: Subject) {
            currentSubject = subject

            subjectTextView.text = subject.subjectName
            subjectImageView.setImageResource(ImgResourceStringToInt.getResourceId(subject.img, itemView.context)
            )

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

    fun updateSelectedPosition(position: Int) {
        val previousPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousPosition)
        notifyItemChanged(selectedPosition)
    }

}

object GoalSubjectDiffCallback : DiffUtil.ItemCallback<Subject>() {
    override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean {
        return oldItem.subjectName == newItem.subjectName
    }
}