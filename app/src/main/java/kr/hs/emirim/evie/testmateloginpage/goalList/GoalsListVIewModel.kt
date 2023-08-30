package kr.hs.emirim.evie.testmateloginpage.goalList

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kr.hs.emirim.evie.testmateloginpage.goalList.data.DataSource
import kr.hs.emirim.evie.testmateloginpage.goalList.data.Goal
import kotlin.random.Random

class GoalsListViewModel(val dataSource: DataSource) : ViewModel() {

    val goalsLiveData = dataSource.getGoalList()

    fun insertGoal() {
        val newGoal = Goal(
            Random.nextLong(),
            "새로운 목표",
            false
        )

        dataSource.addGoal(newGoal)
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