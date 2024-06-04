package kr.hs.emirim.evie.testmateloginpage.wrong_answer_note

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject
import kr.hs.emirim.evie.testmateloginpage.api.WrongAnswerRepository
import kotlin.random.Random


class WrongAnswerListViewModel(val wrongAnswerRepository: WrongAnswerRepository) : ViewModel() {

    val wrongAnswerListData = wrongAnswerRepository.getNoteList()

    fun getLists(grade: Int, subjectId: Int, gradeString: String){
        return wrongAnswerRepository.fetchWrongAnswers(grade, subjectId, gradeString)
    }



    fun insertSubject(subjectName : String?, subjectImage : String?) {
        val newSubject = Subject(
            Random.nextLong(),
            subjectName,
            subjectImage
        )

//        wrongAnswerRepository.addSubject(newSubject)
    }

    fun removeSubject(subject: Subject) {
//        wrongAnswerRepository.removeSubject(subject)
    }


}

//WrongAnswerSubjectViewModel 인스턴스를 생성하는 역할
class WrongAnswerListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WrongAnswerListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WrongAnswerListViewModel(
                wrongAnswerRepository = WrongAnswerRepository.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}