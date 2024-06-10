package kr.hs.emirim.evie.testmateloginpage.subject

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject
import kr.hs.emirim.evie.testmateloginpage.util.ImgResourceStringToInt.Companion.getResourceId


class SubjectHomeAdapter(private val onClick: (Subject) -> Unit) :
    ListAdapter<Subject, SubjectHomeAdapter.SubjectHomeHolder>(SubjectDiffCallback) {

    inner class SubjectHomeHolder(itemView: View, val onClick: (Subject) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val subjectTextView: TextView = itemView.findViewById(R.id.subjectName)
        val subjectImageView: ImageView = itemView.findViewById(R.id.subjectImage)
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

            subjectTextView.setText(subject.subjectName)
            subjectImageView.setImageResource(getResourceId(subject.img, itemView.context))
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectHomeHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.home_subject_layout, parent, false)

        return SubjectHomeHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: SubjectHomeHolder, position: Int) {
        val subject = getItem(position)
        holder.bind(subject)
    }

}

object SubjectDiffCallback : DiffUtil.ItemCallback<Subject>() {
    override fun areItemsTheSame(oldItem: Subject, newItem: Subject): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Subject, newItem: Subject): Boolean {
        return oldItem.subjectName == newItem.subjectName
    }
}