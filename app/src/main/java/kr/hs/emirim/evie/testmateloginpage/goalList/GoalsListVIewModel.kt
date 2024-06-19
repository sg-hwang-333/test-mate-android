package kr.hs.emirim.evie.testmateloginpage.goalList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.emirim.evie.testmateloginpage.api.GoalRepository

import kr.hs.emirim.evie.testmateloginpage.subject.data.SubjectResponse


class GoalsListViewModel(val goalRepository: GoalRepository) : ViewModel() {

    val goalsLiveData = goalRepository.getGoalList()

    fun readGoalList(subject: SubjectResponse, semester : Int) {
        goalRepository.getGoalListBySemester(subject, semester)
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