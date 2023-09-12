package kr.hs.emirim.evie.testmateloginpage.goalList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlin.random.Random

import kr.hs.emirim.evie.testmateloginpage.goalList.data.DataSource
import kr.hs.emirim.evie.testmateloginpage.goalList.data.Goal


class GoalsListViewModel(val dataSource: DataSource) : ViewModel() {

    val goalsLiveData = dataSource.getGoalList()

    fun insertGoal() {
        val newGoal = Goal(
            Random.nextLong(),
            null,
            false
        )

        dataSource.addGoal(newGoal)
    }

    fun removeGoal(goal : Goal) {
        dataSource.removeGoal(goal)
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
                dataSource = DataSource.getDataSource(context.resources)
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}