package kr.hs.emirim.evie.testmateloginpage.goalList.data

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class DataSource(resources: Resources) {
    private val initialGoalList = getInitialGoals()
    private val goalsLiveData = MutableLiveData(initialGoalList)

    fun addGoal(goal: Goal) {
//        goal.id += 1
        // TODO: api 연동해서 DB 에 저장해야 함
        val currentList = goalsLiveData.value
        if (currentList == null) {
            goalsLiveData.postValue(listOf(goal))
        } else {
            val updatedList = currentList.toMutableList() // 불변형 리스트로 변경
            updatedList.add(goal) // 뒤에 나오도록  - 앞으로 나오도록 add(0, goal)
            goalsLiveData.postValue(updatedList) // 관찰자에게 변경 사항 전달
        }
    }

    fun removeGoal(goal: Goal) {
        // TODO: api 연동해서 DB 에 저장해야 함
        val currentList = goalsLiveData.value
        if (currentList != null) {
            val updatedList = currentList.toMutableList()
            updatedList.remove(goal)
            goalsLiveData.postValue(updatedList)
        }
    }

    /* Returns flower given an ID. */
    fun getGoalForId(id: Long): Goal? {  // 특정 ID에 해당하는 목표 데이터를 가져올
        val goals = goalsLiveData.value
        if (goals != null) {
            return goals.firstOrNull { goal -> goal.id == id }
        }
        /*
        goalsLiveData.value?.let { flowers ->
            return flowers.firstOrNull{ it.id == id}
        }
        */
        return null
    }

    fun getGoalList(): LiveData<List<Goal>> {
        return goalsLiveData
    }

    private fun getInitialGoals() = listOf(
        Goal(
            id = 1,
            description = null,
            checked = true,)
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