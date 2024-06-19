package kr.hs.emirim.evie.testmateloginpage.goalList.data

data class GoalResponse(
    val goalId: Int,
    val subjectId: Int,
    val semester: Int,
    val goal: String,
    val completed: Boolean
)
