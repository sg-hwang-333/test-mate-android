package kr.hs.emirim.evie.testmateloginpage.subject.data

data class SubjectUpdateRequest(
    val exams: List<ExamUpdate>,
    val date: String,
    val goalScore: Int,
    val level: Int,
    val comment: String
)

data class ExamUpdate(
    val examName: String,
    val examScore: Int
)


