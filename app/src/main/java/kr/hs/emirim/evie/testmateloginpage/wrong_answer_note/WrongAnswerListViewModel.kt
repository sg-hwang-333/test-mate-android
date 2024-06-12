package kr.hs.emirim.evie.testmateloginpage.wrong_answer_note

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import kr.hs.emirim.evie.testmateloginpage.api.WrongAnswerRepository


class WrongAnswerListViewModel(val wrongAnswerRepository: WrongAnswerRepository) : ViewModel() {

    val wrongAnswerListData = wrongAnswerRepository.getNoteList()

    fun readNoteList(grade: Int, subjectId: Int){
        return wrongAnswerRepository.getNotesByGradeSubject(grade, subjectId)
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