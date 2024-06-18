package kr.hs.emirim.evie.testmateloginpage.api

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kr.hs.emirim.evie.testmateloginpage.TMService
import kr.hs.emirim.evie.testmateloginpage.comm.RetrofitClient
import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data.WrongAnswerNoteResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WrongAnswerRepository(resources: Resources) {

    var wrongAnswerListData = MutableLiveData<List<WrongAnswerNoteResponse>>()

    val wrongAnswerAPIService = RetrofitClient.create(WrongAnswerAPIService::class.java)
    val subjectAPIService = RetrofitClient.create(TMService::class.java)

    fun getNotesByGradeSubject(grade: Int, subjectId: Int) {
        val call = wrongAnswerAPIService.getNoteListByGradeSubject(grade, subjectId)
        call.enqueue(object : Callback<List<WrongAnswerNoteResponse>> {
            override fun onResponse(call: Call<List<WrongAnswerNoteResponse>>, response: Response<List<WrongAnswerNoteResponse>>) {
                if (response.isSuccessful) {
                    val wrongAnswerNoteResponses: List<WrongAnswerNoteResponse>? = response.body()
                    wrongAnswerNoteResponses?.let {
                        wrongAnswerListData.postValue(it)
                    }
                } else {
                    // 서버로부터 응답이 실패한 경우 처리
                    println("Failed to get notes. Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<WrongAnswerNoteResponse>>, t: Throwable) {
                // API 호출 실패 시 처리
                println("Failed to get notes. Error message: ${t.message}")
            }
        })
    }

//    fun getNoteDetail(noteId : Long){
//        val call = wrongAnswerAPIService.getNoteDetail(noteId)
//        call.enqueue(object : Callback<WrongAnswerNoteResponse> {
//            override fun onResponse(call: Call<WrongAnswerNoteResponse>, response: Response<WrongAnswerNoteResponse>) {
//                if (response.isSuccessful) {
//                    val wrongAnswerNote: WrongAnswerNoteResponse? = response.body()
//                    return wrongAnswerNote
//                } else {
//                    // 서버로부터 응답이 실패한 경우 처리
//                    println("Failed to get notes. Error code: ${response.code()}")
//                }
//            }
//
//            override fun onFailure(call: Call<WrongAnswerNoteResponse>, t: Throwable) {
//                // API 호출 실패 시 처리
//                println("Failed to get notes. Error message: ${t.message}")
//            }
//        })
//    }

    suspend fun getNoteDetail(noteId: Long): WrongAnswerNoteResponse? {
        return withContext(Dispatchers.IO) {
            try {
                val response = wrongAnswerAPIService.getNoteDetail(noteId).execute()
                if (response.isSuccessful) {
                    response.body()
                } else {
                    // 서버로부터 응답이 실패한 경우 처리
                    println("Failed to get notes. Error code: ${response.code()}")
                    null
                }
            } catch (e: Exception) {
                // API 호출 실패 시 처리
                println("Failed to get notes. Error message: ${e.message}")
                null
            }
        }
    }

    fun getNoteList(): MutableLiveData<List<WrongAnswerNoteResponse>> {
        return wrongAnswerListData
    }

    fun clearNotesByGrade(grade: Int) {
        val currentList = wrongAnswerListData.value.orEmpty().toMutableList()
        val updatedList = currentList.filter { it.grade.toInt() == grade }
        wrongAnswerListData.postValue(updatedList)
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