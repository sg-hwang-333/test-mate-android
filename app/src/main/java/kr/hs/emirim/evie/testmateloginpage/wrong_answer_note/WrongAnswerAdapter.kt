package kr.hs.emirim.evie.testmateloginpage.wrong_answer_note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject


class WrongAnswerSubjectAdapter(private val onClick: (Subject) -> Unit) :
    ListAdapter<Subject, WrongAnswerSubjectAdapter.WrongAnswerSubjectHolder>(WrongAnswerSubjectDiffCallback) {

    inner class WrongAnswerSubjectHolder(itemView: View, val onClick: (Subject) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val subjectTextView: TextView = itemView.findViewById(R.id.wrongAnswerSubject)
        var currentSubject: Subject? = null

        private lateinit var subjectEditBtn: Button

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

            subjectTextView.setText(subject.name)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WrongAnswerSubjectHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wrong_answer_subject_layout, parent, false)

        return WrongAnswerSubjectHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: WrongAnswerSubjectHolder, position: Int) {
        val subject = getItem(position)
        holder.bind(subject)
    }

}



object WrongAnswerSubjectDiffCallback : DiffUtil.ItemCallback<Subject>() {
    override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean {
        return oldItem.id == newItem.id
    }
}