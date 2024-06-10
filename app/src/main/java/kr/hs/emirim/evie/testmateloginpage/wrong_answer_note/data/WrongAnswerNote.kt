package kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data

data class WrongAnswerNote(
    val noteId : Long,
    val subjectId : Int,
    val grade : String,
    val title : String,
    val imgs : String,
    val reason : String,
    val range : String
)