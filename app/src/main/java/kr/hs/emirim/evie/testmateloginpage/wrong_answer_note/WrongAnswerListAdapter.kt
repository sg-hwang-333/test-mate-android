package kr.hs.emirim.evie.testmateloginpage.wrong_answer_note

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.util.ImgResourceStringToInt
import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data.WrongAnswerNoteResponse


class WrongAnswerListAdapter(private val onClick: (WrongAnswerNoteResponse, Int) -> Unit) :
    ListAdapter<WrongAnswerNoteResponse, WrongAnswerListAdapter.WrongAnswerListHolder>(WrongAnswerListDiffCallback) {

    val gradeStringList = arrayOf("중학교 1학년", "중학교 2학년", "중학교 3학년", "고등학교 1학년", "고등학교 2학년", "고등학교 3학년")
    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class WrongAnswerListHolder(itemView: View, val onClick: (WrongAnswerNoteResponse, Int) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        val listGrade: TextView = itemView.findViewById(R.id.wan_list_grade)
        val listTitle: TextView = itemView.findViewById(R.id.wan_list_title)
        val listReason: android.widget.Button = itemView.findViewById(R.id.wan_list_reason)
        val listImg: ImageView = itemView.findViewById(R.id.wan_list_img)
        var currentWANList: WrongAnswerNoteResponse? = null

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onClick(getItem(position), position)
                }
            }
        }

        /* UI에 정보 바인딩(넣는 메서드) */
        fun bind(response: WrongAnswerNoteResponse) {
            currentWANList = response
            listGrade.setText(gradeStringList[response.grade.toInt() - 1])
            listTitle.setText(response.title)
            listReason.setText(response.reason)
            loadNoteImage(getImageFirstUrl(response.imgs)!!)

            // String 타입 이미지 주소를 int로 전환해 setImageResource
//            listImg.setImageResource(ImgResourceStringToInt.getResourceId(response.imgs, itemView.context))
        }

        private fun loadNoteImage(imageUrl: String) {
            Glide.with(itemView.context)
                .load(imageUrl)
                .placeholder(R.drawable.placeholder) // 로딩 중 표시할 이미지
                .error(R.drawable.img_error) // 에러 시 표시할 이미지
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        Log.e("Glide", "Image load failed", e)
                        Log.d("Glide", imageUrl)
                        return false // false를 반환하여 Glide가 기본적으로 설정한 에러 이미지를 표시하지 않도록 합니다.
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: com.bumptech.glide.request.target.Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        // 이미지가 성공적으로 로드되었을 때 처리할 로직
                        return false
                    }
                })
                .into(listImg)
        }

        // 이미지 리스트 생성 메서드
        fun parseImageListUrl(imageUrlString: String): List<String> {
            // 문자열에서 대괄호와 쉼표를 제거합니다.
            val cleanedString = imageUrlString
                .replace("[", "")
                .replace("]", "")
                .replace(",", "")

            // 공백을 기준으로 잘라서 배열로 만듭니다.
            val imageUrlArray = cleanedString.split(" ")

            // 결과를 반환합니다.
            return imageUrlArray
        }

        fun getImageFirstUrl(imageUrlString: String): String? {
            // 문자열에서 대괄호와 쉼표를 제거합니다.
            val cleanedString = imageUrlString
                .replace("[", "")
                .replace("]", "")
                .replace(",", "")

            // 공백을 기준으로 잘라서 배열로 만듭니다.
            val imageUrlArray = cleanedString.split(" ")

            // 배열의 첫 번째 요소를 반환합니다.
            return imageUrlArray.firstOrNull()
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

object WrongAnswerListDiffCallback : DiffUtil.ItemCallback<WrongAnswerNoteResponse>() {
    override fun areItemsTheSame(oldItem: WrongAnswerNoteResponse, newItem: WrongAnswerNoteResponse): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: WrongAnswerNoteResponse, newItem: WrongAnswerNoteResponse): Boolean {
        return oldItem.noteId == newItem.noteId
    }
}