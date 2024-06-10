package kr.hs.emirim.evie.testmateloginpage.api

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import kr.hs.emirim.evie.testmateloginpage.TMService
import kr.hs.emirim.evie.testmateloginpage.comm.RetrofitClient
import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data.WrongAnswerNote
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WrongAnswerRepository(resources: Resources) {

    var wrongAnswerListData = MutableLiveData<List<WrongAnswerNote>>()

    val wrongAnswerAPIService = RetrofitClient.create(WrongAnswerAPIService::class.java)
    val subjectAPIService = RetrofitClient.create(TMService::class.java)

    fun getNotesByGradeSubject(grade: Int, subjectId: Int) {
        val call = wrongAnswerAPIService.getNotesByGradeSubject(grade, subjectId)
        call.enqueue(object : Callback<List<WrongAnswerNote>> {
            override fun onResponse(call: Call<List<WrongAnswerNote>>, response: Response<List<WrongAnswerNote>>) {
                if (response.isSuccessful) {
                    val wrongAnswerNotes: List<WrongAnswerNote>? = response.body()
                    wrongAnswerNotes?.let {
                        wrongAnswerListData.postValue(it)
                    }
                } else {
                    // 서버로부터 응답이 실패한 경우 처리
                    println("Failed to get notes. Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<WrongAnswerNote>>, t: Throwable) {
                // API 호출 실패 시 처리
                println("Failed to get notes. Error message: ${t.message}")
            }
        })
    }

    fun getNoteList(): MutableLiveData<List<WrongAnswerNote>> {
        return wrongAnswerListData
    }


    companion object { // 데이터를 관리하고 제공하는 데 사용되는 클래스의 인스턴스를 만들지 않고도 호출하게 함
        private var INSTANCE: WrongAnswerRepository? = null
        fun getDataSource(resources: Resources): WrongAnswerRepository {
            return synchronized(WrongAnswerRepository::class) {
                val newInstance = INSTANCE ?: WrongAnswerRepository(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}