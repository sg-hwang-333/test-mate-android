package kr.hs.emirim.evie.testmateloginpage.api

import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data.WrongAnswerNote
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface WrongAnswerAPIService {

    @GET("/api/note/filter")
    fun getNotesByGradeSubject(@Query("grade") grade: Int, @Query("subjectId") subjectId : Int) : Call<List<WrongAnswerNote>>

    @POST("/api/note")
    fun postNote(@Body wrongAnswerNote : WrongAnswerNote) : Call<MessageResponse>
}