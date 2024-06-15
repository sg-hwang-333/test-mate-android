package kr.hs.emirim.evie.testmateloginpage.subject.data

data class SubjectUpdateRequest(
    val exams: List<ExamRequest>,
    val date: String,
    val goalScore: Int,
    val level: Int,
    val comment: String
) {
    data class ExamRequest(
        val examName: String,
        val examScore: Int
    )
}

