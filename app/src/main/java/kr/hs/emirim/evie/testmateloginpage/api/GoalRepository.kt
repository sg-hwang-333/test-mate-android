package kr.hs.emirim.evie.testmateloginpage.api

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kr.hs.emirim.evie.testmateloginpage.comm.RetrofitClient
import kr.hs.emirim.evie.testmateloginpage.goalList.data.Goal
import kr.hs.emirim.evie.testmateloginpage.goalList.data.GoalPatchRequest
import kr.hs.emirim.evie.testmateloginpage.goalList.data.GoalRequest
import kr.hs.emirim.evie.testmateloginpage.goalList.data.GoalResponse
import kr.hs.emirim.evie.testmateloginpage.subject.data.SubjectRequest
import kr.hs.emirim.evie.testmateloginpage.subject.data.SubjectResponse
import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data.WrongAnswerNoteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GoalRepository(resources: Resources, context: Context) {
    var goalListData = MutableLiveData<List<GoalResponse>>()

    val goalListService : GoalAPIService

    init {
        goalListService = RetrofitClient.create(GoalAPIService::class.java, context)
    }

    fun getGoalList(): MutableLiveData<List<GoalResponse>> {
        return goalListData
    }

    fun getGoalListBySemester(subjectId: Int, semester : Int) {
        val call = goalListService.getGoalListBySemester(subjectId, semester)
        call.enqueue(object : Callback<List<GoalResponse>> {
            override fun onResponse(call: Call<List<GoalResponse>>, response: Response<List<GoalResponse>>) {
                if (response.isSuccessful) {
                    val goalList = response.body()
                    goalList?.let {
                        goalListData.postValue(it) // 받은 과목 정보를 MutableLiveData에 저장
                    }
                } else {
                    Log.e("API Error", "Failed to fetch subject info. Error code: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<List<GoalResponse>>, t: Throwable) {
                println("Failed to get notes. Error message: ${t.message}")
            }
        })
    }

    fun postGoal(goal : GoalRequest, callback: (Boolean) -> Unit) {
        val call = goalListService.postGoal(goal)
        call.enqueue(object : Callback<MessageResponse> {
            override fun onResponse(call: Call<MessageResponse>, response: Response<MessageResponse>) {
                if (response.isSuccessful) {
                    callback(true)
                } else {
                    callback(false)
                }
            }
            override fun onFailure(call: Call<MessageResponse>, t: Throwable) {
                callback(false)
                println("Failed to get notes. Error message: ${t.message}")
            }
        })
    }

    fun patchGoal(goalId : Int, goal: GoalPatchRequest, callback: (Boolean, GoalResponse) -> Unit) {
        val call = goalListService.patchGoal(goalId, goal)
        call.enqueue(object : Callback<GoalResponse> {
            override fun onResponse(call: Call<GoalResponse>, response: Response<GoalResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { callback(true, it) }
                } else {
                    response.body()?.let { callback(false, it) }
                }
            }

            override fun onFailure(call: Call<GoalResponse>, t: Throwable) {

            }
        })
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
//    fun getGoalForId(id: Long): Goal? {  // 특정 ID에 해당하는 목표 데이터를 가져올
//        val goals = goalListData.value
//        if (goals != null) {
//            return goals.firstOrNull { goal -> goal.goalId == id }
//        }
//        return null
//    }




    companion object { // 데이터를 관리하고 제공하는 데 사용되는 클래스의 인스턴스를 만들지 않고도 호출하게 함
        private var INSTANCE: GoalRepository? = null

        fun getDataSource(resources: Resources, context: Context): GoalRepository {
            return synchronized(GoalRepository::class) {
                val newInstance = INSTANCE ?: GoalRepository(resources, context)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}