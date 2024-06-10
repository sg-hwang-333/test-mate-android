package kr.hs.emirim.evie.testmateloginpage.subject

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.emirim.evie.testmateloginpage.api.SubjectRepository

import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject


class SubjectViewModel(val subjectRepository: SubjectRepository) : ViewModel() {

    val subjectListData = subjectRepository.getSubjectList()

    fun readSubjectList(grade: Int) {
        subjectRepository.getSubjects(grade)
    }

    fun createSubject(subject: Subject) {
        subjectRepository.postSubject(subject)
    }
}

//WrongAnswerSubjectViewModel 인스턴스를 생성하는 역할
class SubjectViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubjectViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SubjectViewModel(
                subjectRepository = SubjectRepository.getDataSource(context.resources, context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}