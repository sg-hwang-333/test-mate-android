package kr.hs.emirim.evie.testmateloginpage.subject

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import kr.hs.emirim.evie.testmateloginpage.subject.data.SubjectDataEdit
import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject
import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data.WrongAnswer
import kotlin.random.Random


class WrongAnswerSubjectViewModel(val subjectDataEdit: SubjectDataEdit) : ViewModel() {

    val subjectsLiveData = subjectDataEdit.getSubjectList()

    fun insertSubject(subjectName : String?, subjectImage : String?) {
        val newSubject = Subject(
            Random.nextLong(),
            subjectName,
            subjectImage
        )

        subjectDataEdit.addSubject(newSubject)
    }

    fun removeSubject(subject: Subject) {
        subjectDataEdit.removeSubject(subject)
    }
}

//WrongAnswerSubjectViewModel 인스턴스를 생성하는 역할
class WrongAnswerSubjectsViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WrongAnswerSubjectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WrongAnswerSubjectViewModel(
                subjectDataEdit = SubjectDataEdit.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}