package kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

data class WrongAnswer(
    val noteId : Long,
    val subjectId : Int,
    val grade : Int,
    val title : String,
    val imgs : String,
    val reason : String,
    val range : String
)



interface WrongAnswerAPIService {
    @GET("/api/note/filter")
    fun getNotesByGradeSubject(@Query("grade") grade: Int, @Query("subjectId") subjectId : Int) : Call<WrongAnswerListResponse>
}