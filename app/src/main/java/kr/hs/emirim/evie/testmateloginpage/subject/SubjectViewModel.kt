package kr.hs.emirim.evie.testmateloginpage.subject

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.emirim.evie.testmateloginpage.api.SubjectRepository

import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject


class SubjectViewModel(val subjectRepository: SubjectRepository) : ViewModel() {

    val subjectListData = subjectRepository.getSubjectList()

    fun getLists() : MutableLiveData<List<Subject>> {
        return subjectListData
    }

    fun addList(subject: Subject){
        subjectRepository.fetchSubject(subject)
    }

//    fun insertSubject(grade : Int, subjectName : String?, subjectImage : String?) {
//        val newSubject = Subject(
//            grade,
//            subjectName,
//            subjectImage
//        )
//
//        subjectListData.addSubject(newSubject)
//    }
//    fun removeSubject(subject: Subject) {
//        subjectListData.removeSubject(subject)
//    }
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