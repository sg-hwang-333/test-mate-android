//package kr.hs.emirim.evie.testmateloginpage.home
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//import kr.hs.emirim.evie.testmateloginpage.home.data.HomeSubjectInfoResponse
//
//class ExamsAdapter(private var exams: MutableList<HomeSubjectInfoResponse.Exam>) :
//    RecyclerView.Adapter<ExamsAdapter.ExamViewHolder>() {
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExamViewHolder {
//        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_exam, parent, false)
//        return ExamViewHolder(view)
//    }
//
//    override fun onBindViewHolder(holder: ExamViewHolder, position: Int) {
//        val exam = exams[position]
//        holder.examNameTextView.text = exam.examName
//        holder.examScoreTextView.text = exam.examScore.toString()
//    }
//
//    override fun getItemCount(): Int {
//        return exams.size
//    }
//
//    fun updateExams(newExams: List<HomeSubjectInfoResponse.Exam>) {
//        exams.clear()
//        exams.addAll(newExams)
//        notifyDataSetChanged()
//    }
//
//    class ExamViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
//        val examNameTextView: TextView = itemView.findViewById(R.id.examNameTextView)
//        val examScoreTextView: TextView = itemView.findViewById(R.id.examScoreTextView)
//    }
//}
