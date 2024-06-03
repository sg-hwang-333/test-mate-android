package kr.hs.emirim.evie.testmateloginpage.subject

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject
import kr.hs.emirim.evie.testmateloginpage.subject.data.SubjectDataEdit
import kotlin.random.Random


class GoalMainSubjectsViewModel(val dataSource: SubjectDataEdit) : ViewModel() {

    val goalSubjectsLiveData = dataSource.getSubjectList()

//    val image = dataSource.getRandomFlowerImageAsset()
    fun insertSubject(subjectName : String?) {
        val newSubject = Subject(
            Random.nextLong(),
            subjectName,
            img = "e"
        )

        dataSource.addSubject(newSubject)
    }

    fun removeSubject(subject: Subject) {
        dataSource.removeSubject(subject)
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
                dataSource = SubjectDataEdit.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}