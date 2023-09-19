package kr.hs.emirim.evie.testmateloginpage.goalmain.data

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject

class DataSource(resources: Resources) {
    private val goalSubjectsLiveData = MutableLiveData(getInitialSubjects())

    fun addSubject(subject: Subject) {
        // TODO: api 연동해서 DB 에 저장해야 함
        val currentList = goalSubjectsLiveData.value
        if (currentList == null) {
            goalSubjectsLiveData.postValue(listOf(subject))
        } else {
            val updatedList = currentList.toMutableList() // 불변형 리스트로 변경
            updatedList.add(subject) // 뒤에 나오도록  - 앞으로 나오도록 add(0, goal)
            goalSubjectsLiveData.postValue(updatedList) // 관찰자에게 변경 사항 전달
        }
    }

    fun removeSubject(subject: Subject) {
        // TODO: api 연동해서 DB 에 저장해야 함
        val currentList = goalSubjectsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(subject)
            goalSubjectsLiveData.postValue(updatedList)
        }
    }

    fun getSubjectList(): LiveData<List<Subject>> {
        return goalSubjectsLiveData
    }

    private fun getInitialSubjects() = listOf(
        Subject(
            id = 1,
            name = "국어",
            ),
        Subject(
            id = 2,
            name = "수학",
        ),
        Subject(
            id = 3,
            name = "과학",
        )
//        ),
//        Goal(
//            id = 2,
//            description = "기본적인 문학 용어들 파악하기",
//            checked = false,
//        ),
//        Goal(
//            id = 3,
//            description = "독서량을 늘려서 읽는 속도 향상시키기",
//            checked = false,
//        ),
//        Goal(
//            id = 4,
//            description = "나눠주신 자료 문제 분석하기",
//            checked = false,
//        ),
//        Goal(
//            id = 5,
//            description = "작품에 대한 사전정보 암기하기",
//            checked = false,
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