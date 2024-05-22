package kr.hs.emirim.evie.testmateloginpage.goalList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.random.Random

import kr.hs.emirim.evie.testmateloginpage.goalList.data.GoalListDataEdit
import kr.hs.emirim.evie.testmateloginpage.goalList.data.Goal


class GoalsListViewModel(val goalListDataEdit: GoalListDataEdit) : ViewModel() {

    val goalsLiveData = goalListDataEdit.getGoalList()

    fun insertGoal() {
        val newGoal = Goal(
            Random.nextLong(),
            null,
            false
        )

        goalListDataEdit.addGoal(newGoal)
    }

    fun removeGoal(goal : Goal) {
        goalListDataEdit.removeGoal(goal)
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
                goalListDataEdit = GoalListDataEdit.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}