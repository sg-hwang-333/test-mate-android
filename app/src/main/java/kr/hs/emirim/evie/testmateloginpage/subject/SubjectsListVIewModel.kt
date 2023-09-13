package kr.hs.emirim.evie.testmateloginpage.subject

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import kr.hs.emirim.evie.testmateloginpage.subject.data.DataSource
import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject
import kotlin.random.Random


class SubjectsListVIewModel(val dataSource: DataSource) : ViewModel() {

    val subjectsLiveData = dataSource.getSubjectList()

//    val image = dataSource.getRandomFlowerImageAsset()
    fun insertSubject(subjectName:String) {
        val newSubject = Subject(
            Random.nextLong(),
            subjectName,
//            image
        )

        dataSource.addSubject(newSubject)
    }

    fun removeSubject(subject: Subject) {
        dataSource.removeSubject(subject)
    }

    fun getSubjectCount(): Int {
        val subjectList = subjectsLiveData.value
        return subjectList?.size ?: 0
    }
}

class GoalsListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubjectsListVIewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SubjectsListVIewModel(
                dataSource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}