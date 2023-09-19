package kr.hs.emirim.evie.testmateloginpage.goalList.data

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import android.util.Log
import kr.hs.emirim.evie.testmateloginpage.GoalListResponse
import kr.hs.emirim.evie.testmateloginpage.TMService
import kr.hs.emirim.evie.testmateloginpage.goalList.GoalsAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataSource(resources: Resources) {
    private val initialGoalList = getInitialGoals()
    private val goalsLiveData = MutableLiveData(initialGoalList)

    var retrofit = Retrofit.Builder()
        .baseUrl("http://3.36.171.123:8086")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var goalListService = retrofit.create(TMService::class.java)

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

//    fun removeGoal(goal: Goal) {
//        val requestBody = mapOf("id" to goal.id) // RemoveRequestBody는 삭제 요청 바디를 나타내는 데이터 클래스입니다.
//        val call = goalListService.requestGoalDelete(requestBody) // requestRemoveGoal 함수는 API 호출을 나타내며, API 서비스 인터페이스에 정의되어 있어야 합니다.
//
//        // API 호출 실행
//        call.enqueue(object : Callback<GoalListResponse> {
//            override fun onResponse(call: Call<GoalListResponse>, response: Response<GoalListResponse>) {
//                val code = response.code()
//                if(code == 200) {
//                    // 성공적으로 삭제된 경우
//                    val apiResponse = response.body()
////                    if (apiResponse != null && apiResponse.success) {
////                        // API 응답에서 성공 여부 확인
////                        // 처리 코드 추가
////                    } else {
////                        // 처리 실패
////                    }
//                } else {
//                    Log.d("mytag", "fail at API")
//                }
//            }
//
//            override fun onFailure(call: Call<GoalListResponse>, t: Throwable) {
//                Log.d("mytag", "fail " + t.message)
//            }
//        })
//    }

    /* Returns flower given an ID. */
    fun getGoalForId(id: Long): Goal? {  // 특정 ID에 해당하는 목표 데이터를 가져올
        val goals = goalsLiveData.value
        if (goals != null) {
            return goals.firstOrNull { goal -> goal.id == id }
        }
        return null
    }

    fun getGoalList(): LiveData<List<Goal>> {
        return goalsLiveData
    }


    private fun getInitialGoals() = listOf(
        Goal(
            id = 1,
            description = null,
            checked = true
        )
        // 다른 초기 목표를 여기에 추가할 수 있습니다.
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

//class DataSource(resources: Resources) {
//    private val initialGoalList = getInitialGoals()
//    private val goalsLiveData = MutableLiveData(initialGoalList)
//
//    fun addGoal(goal: Goal) {
////        goal.id += 1
//        // TODO: api 연동해서 DB 에 저장해야 함
//        val currentList = goalsLiveData.value
//        if (currentList == null) {
//            goalsLiveData.postValue(listOf(goal))
//        } else {
//            val updatedList = currentList.toMutableList() // 불변형 리스트로 변경
//            updatedList.add(goal) // 뒤에 나오도록  - 앞으로 나오도록 add(0, goal)
//            goalsLiveData.postValue(updatedList) // 관찰자에게 변경 사항 전달
//        }
//    }
//
//    fun removeGoal(goal: Goal) {
//        // TODO: api 연동해서 DB 에 저장해야 함
//        val currentList = goalsLiveData.value
//        if (currentList != null) {
//            val updatedList = currentList.toMutableList()
//            updatedList.remove(goal)
//            goalsLiveData.postValue(updatedList)
//        }
//    }
//
//    /* Returns flower given an ID. */
//    fun getGoalForId(id: Long): Goal? {  // 특정 ID에 해당하는 목표 데이터를 가져올
//        val goals = goalsLiveData.value
//        if (goals != null) {
//            return goals.firstOrNull { goal -> goal.id == id }
//        }
//        /*
//        goalsLiveData.value?.let { flowers ->
//            return flowers.firstOrNull{ it.id == id}
//        }
//        */
//        return null
//    }
//
//    fun getGoalList(): LiveData<List<Goal>> {
//        return goalsLiveData
//    }
//
//    private fun getInitialGoals() = listOf(
//        Goal(
//            id = 1,
//            description = null,
//            checked = true,)
////        ),
////        Goal(
////            id = 2,
////            description = "기본적인 문학 용어들 파악하기",
////            checked = false,
////        ),
////        Goal(
////            id = 3,
////            description = "독서량을 늘려서 읽는 속도 향상시키기",
////            checked = false,
////        ),
////        Goal(
////            id = 4,
////            description = "나눠주신 자료 문제 분석하기",
////            checked = false,
////        ),
////        Goal(
////            id = 5,
////            description = "작품에 대한 사전정보 암기하기",
////            checked = false,
////        )
//    )
//    companion object { // 데이터를 관리하고 제공하는 데 사용되는 클래스의 인스턴스를 만들지 않고도 호출하게 함
//        private var INSTANCE: DataSource? = null
//
//        fun getDataSource(resources: Resources): DataSource {
//            return synchronized(DataSource::class) {
//                val newInstance = INSTANCE ?: DataSource(resources)
//                INSTANCE = newInstance
//                newInstance
//            }
//        }
//    }
//}