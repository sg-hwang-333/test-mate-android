package kr.hs.emirim.evie.testmateloginpage.wrong_answer_note

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.util.ImgResourceStringToInt
import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data.WrongAnswerNote


class WrongAnswerListAdapter(private val onClick: (WrongAnswerNote, Int) -> Unit) :
    ListAdapter<WrongAnswerNote, WrongAnswerListAdapter.WrongAnswerListHolder>(WrongAnswerListDiffCallback) {

    val gradeStringList = arrayOf("중학교 1학년", "중학교 2학년", "중학교 3학년", "고등학교 1학년", "고등학교 2학년", "고등학교 3학년")
    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class WrongAnswerListHolder(itemView: View, val onClick: (WrongAnswerNote, Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val listGrade: TextView = itemView.findViewById(R.id.wan_list_grade)
        val listTitle: TextView = itemView.findViewById(R.id.wan_list_title)
        val listReason: android.widget.Button = itemView.findViewById(R.id.wan_list_reason)
        val listImg: ImageView = itemView.findViewById(R.id.wan_list_img)
        var currentWANList: WrongAnswerNote? = null

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onClick(getItem(position), position)
                }
            }
        }

        /* UI에 정보 바인딩(넣는 메서드) */
        fun bind(response: WrongAnswerNote) {
            currentWANList = response
            listGrade.setText(gradeStringList[response.grade.toInt() - 1])
            listTitle.setText(response.title)
            listReason.setText(response.reason)

            // String 타입 이미지 주소를 int로 전환해 setImageResource
            listImg.setImageResource(ImgResourceStringToInt.getResourceId(response.imgs, itemView.context))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WrongAnswerListHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.wrong_answer_object_layout, parent, false)

        return WrongAnswerListHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: WrongAnswerListHolder, position: Int) {
        val wrongAnswer = getItem(position)
        holder.bind(wrongAnswer)
    }

    fun updateSelectedPosition(position: Int) {
        val previousPosition = selectedPosition
        selectedPosition = position
        notifyItemChanged(previousPosition)
        notifyItemChanged(selectedPosition)
    }

}

object WrongAnswerListDiffCallback : DiffUtil.ItemCallback<WrongAnswerNote>() {
    override fun areItemsTheSame(oldItem: WrongAnswerNote, newItem: WrongAnswerNote): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WrongAnswerNote, newItem: WrongAnswerNote): Boolean {
        return oldItem.noteId == newItem.noteId
    }
}