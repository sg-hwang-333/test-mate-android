package kr.hs.emirim.evie.testmateloginpage.api

import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data.WrongAnswerListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WrongAnswerAPIService {

    @GET("/api/note/filter")
    fun getNotesByGradeSubject(@Query("grade") grade: Int, @Query("subjectId") subjectId : Int) : Call<List<WrongAnswerListResponse>>
}