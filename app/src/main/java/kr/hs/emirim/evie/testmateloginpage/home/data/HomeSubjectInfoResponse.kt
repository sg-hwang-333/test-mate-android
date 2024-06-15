package kr.hs.emirim.evie.testmateloginpage.home.data

data class HomeSubjectInfoResponse(
    val subjectId: Int,
    val exams: List<Exam>,
    val date: String,
    val level: Int,
    val goalScore: Int,
    val fail: Int
) {
    data class Exam(
        val id: Int,
        val examName: String,
        val examScore: Int
    )
}
