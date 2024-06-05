package kr.hs.emirim.evie.testmateloginpage.subject.data

data class Subject(
    var grade: Int,
    var subjectName: String?,
    val img: String?
)

//data class Subject(
//    val subjectId: Long,
//    val userId: Long,
//    val grade: Int,
//    val pastExams: List<String>?, // null 허용 리스트
//    val date: String?, // null 허용 문자열
//    val goalScore: Int,
//    val level: Int,
//    val comment: Int,
//    val img: String,
//    val fail: Int
//)