package kr.hs.emirim.evie.testmateloginpage.api

import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data.WrongAnswerNoteRequest
import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data.WrongAnswerNoteResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface WrongAnswerAPIService {

    @GET("/api/note/filter")
    fun getNoteListByGradeSubject(@Query("grade") grade: Int, @Query("subjectId") subjectId : Int) : Call<List<WrongAnswerNoteResponse>>

    @POST("/api/note")
    fun postNote(@Body wrongAnswerNoteRequest : WrongAnswerNoteRequest) : Call<MessageResponse>

    @GET("api/note/{noteId}")
    fun getNoteDetail(@Path("noteId") noteId: Long) : Call<WrongAnswerNoteResponse>
}