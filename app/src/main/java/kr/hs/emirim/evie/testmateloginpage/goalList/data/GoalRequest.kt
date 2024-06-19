package kr.hs.emirim.evie.testmateloginpage.goalList.data

data class GoalRequest(
    val subjectId: Int,
    val semester: Int,
    val goal: String,
    val completed: Boolean
)
