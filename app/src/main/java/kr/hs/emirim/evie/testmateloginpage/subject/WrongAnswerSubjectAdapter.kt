package kr.hs.emirim.evie.testmateloginpage.subject

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject


class WrongAnswerSubjectAdapter(private val onClick: (Subject, Int) -> Unit) :
    ListAdapter<Subject, WrongAnswerSubjectAdapter.WrongAnswerSubjectHolder>(WrongAnswerSubjectDiffCallback) {

    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class WrongAnswerSubjectHolder(itemView: View, val onClick: (Subject, Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val subjectView: Button = itemView.findViewById(R.id.wrongAnswerSubject)

        init {
            subjectView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onClick(getItem(position), position)
                }
            }
        }

        /* UI에 정보 바인딩(넣는 메서드) */
        fun bind(subject: Subject, isSelected: Boolean) {
            subjectView.text = subject.subjectName
            updateUI(isSelected)
        }

        private fun updateUI(isSelected: Boolean) {
            if (isSelected) {
                subjectView.setBackgroundResource(R.drawable.btn_border_bottom_green)
                subjectView.setTextColor(ContextCompat.getColor(subjectView.context, R.color.green_500))
            } else {
                subjectView.setBackgroundResource(0)
                subjectView.setTextColor(ContextCompat.getColor(subjectView.context, R.color.black_300))
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WrongAnswerSubjectHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wrong_answer_subject_layout, parent, false)

        return WrongAnswerSubjectHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: WrongAnswerSubjectHolder, position: Int) {
        val subject = getItem(position)
        holder.bind(subject, position == selectedPosition)
    }

    fun updateSelectedPosition(position: Int) {
        val previousPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousPosition)
        notifyItemChanged(selectedPosition)
    }

}



object WrongAnswerSubjectDiffCallback : DiffUtil.ItemCallback<Subject>() {
    override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean {
        return oldItem.subjectName == newItem.subjectName
    }
}