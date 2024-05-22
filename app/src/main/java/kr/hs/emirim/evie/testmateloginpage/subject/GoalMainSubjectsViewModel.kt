package kr.hs.emirim.evie.testmateloginpage.subject

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import kr.hs.emirim.evie.testmateloginpage.subject.data.SubjectDataEdit
import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject
import kotlin.random.Random


class GoalMainSubjectsViewModel(val subjectDataEdit: SubjectDataEdit) : ViewModel() {

    val goalSubjectsLiveData = subjectDataEdit.getSubjectList()

//    val image = dataSource.getRandomFlowerImageAsset()
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

    fun getSubjectCount(): Int {
        val subjectList = goalSubjectsLiveData.value
        return subjectList?.size ?: 0
    }
}

class GoalMainViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoalMainSubjectsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GoalMainSubjectsViewModel(
                subjectDataEdit = SubjectDataEdit.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}