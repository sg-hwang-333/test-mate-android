package kr.hs.emirim.evie.testmateloginpage.subject.data

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DataSource(resources: Resources) {
    private val initialFlowerList = getInitialSubjects()
    private val subjectsLiveData = MutableLiveData(initialFlowerList)

    fun addSubject(subject: Subject) {
        val currentList = subjectsLiveData.value
        if (currentList == null) {
            subjectsLiveData.postValue(listOf(subject))
        } else {
            val updatedList = currentList.toMutableList() // 불변형 리스트로 변경
            updatedList.add(subject) // 뒤에 나오도록  - 앞으로 나오도록 add(0, goal)
            subjectsLiveData.postValue(updatedList) // 관찰자에게 변경 사항 전달
        }
    }

    fun removeSubject(subject: Subject) {
        val currentList = subjectsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(subject)
            subjectsLiveData.postValue(updatedList)
        }
    }

    fun getSubjectList(): LiveData<List<Subject>> {
        return subjectsLiveData
    }

    private fun getInitialSubjects() = listOf(
        Subject(
            id = 1,
            name = "국어",
            )
//        Subject(
//            id = 2,
//            name = "수학",
//        ),
//        Subject(
//            id = 3,
//            name = "과학",
//        ),
//        Subject(
//            id = 4,
//            name = "역사",
//        ),
//        Subject(
//            id = 5,
//            name = "자바",
//        )
    )
    companion object { // 데이터를 관리하고 제공하는 데 사용되는 클래스의 인스턴스를 만들지 않고도 호출하게 함
        private var INSTANCE: DataSource? = null

        fun getDataSource(resources: Resources): DataSource {
            return synchronized(DataSource::class) {
                val newInstance = INSTANCE ?: DataSource(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}