package kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data

data class WrongAnswerListResponse(
    val noteId : Long,
    val subjectId : Int,
    val grade : String?,
    val title : String,
    val imgs : String,
    val reason : String
)