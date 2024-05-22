package kr.hs.emirim.evie.testmateloginpage.subject

import android.content.Context
import android.content.res.Resources
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


class GoalSubjectTabAdapter(private val onClick: (Subject) -> Unit) :
    ListAdapter<Subject, GoalSubjectTabAdapter.GoalSubjectTabHolder>(GoalSubjectDiffCallback) {

    inner class GoalSubjectTabHolder(itemView: View, val onClick: (Subject) -> Unit) :
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

            subjectTextView.text = subject.name
            val resourceId = getResource(subject.image, itemView.context)
            subjectImageView.setImageResource(resourceId)
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

    private fun getResource(resName: String?, context: Context) : Int {
        val resContext = context.createPackageContext(context.packageName, 0)
        val res: Resources = resContext.resources

        val id = res.getIdentifier(resName, "drawable", context.packageName)
        return id
//            ContextCompat.getDrawable(context, R.drawable.book_red)
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