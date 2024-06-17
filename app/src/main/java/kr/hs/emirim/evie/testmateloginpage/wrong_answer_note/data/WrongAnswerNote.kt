package kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data

import android.os.Parcelable
import java.io.Serializable

data class WrongAnswerNote(
    val noteId : Long,
    val subjectId : Int,
    val grade : Int,
    val title : String,
    val imgs : String,
    val styles : String,
    val reason : String,
    val range : String
) : Serializable