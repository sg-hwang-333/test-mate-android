package kr.hs.emirim.evie.testmateloginpage.goalList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.emirim.evie.testmateloginpage.api.GoalRepository
import kr.hs.emirim.evie.testmateloginpage.goalList.data.GoalPatchRequest
import kr.hs.emirim.evie.testmateloginpage.goalList.data.GoalRequest

import kr.hs.emirim.evie.testmateloginpage.subject.data.SubjectResponse


class GoalsListViewModel(val goalRepository: GoalRepository) : ViewModel() {

    val goalsLiveData = goalRepository.getGoalList()

    fun readGoalList(subjectId : Int, semester : Int) {
        goalRepository.getGoalListBySemester(subjectId, semester)
    }

    fun createGoal(goal : GoalRequest) {
        goalRepository.postGoal(goal) { isSuccess ->
            if (isSuccess) {
                // 성공적으로 목표가 추가되었을 때 목록을 다시 불러옴
                readGoalList(goal.subjectId, goal.semester)
            } else {
                // 실패 시 처리 (예: 에러 메시지 표시)
            }
        }
    }

    fun updateGoal(goal: GoalPatchRequest) {
        goalRepository.patchGoal(goal) { isSuccess, response ->
            if (isSuccess) {
                readGoalList(response.subjectId, response.semester)
            } else {
                // 실패 시 처리 (예: 에러 메시지 표시)
            }
        }
    }

    fun removeGoal(subjectId : Int, semester : Int) {
//        goalRepository.getGoalListBySemester()
    }

    fun getGoalCount(): Int {
        val goalList = goalsLiveData.value
        return goalList?.size ?: 0
    }
}

class GoalsListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GoalsListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GoalsListViewModel(
                goalRepository = GoalRepository.getDataSource(context.resources, context)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}