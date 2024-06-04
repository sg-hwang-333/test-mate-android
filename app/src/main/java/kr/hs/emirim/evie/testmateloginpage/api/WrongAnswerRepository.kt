package kr.hs.emirim.evie.testmateloginpage.api

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kr.hs.emirim.evie.testmateloginpage.TMService
import kr.hs.emirim.evie.testmateloginpage.comm.RetrofitClient
import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data.WrongAnswerListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WrongAnswerRepository(resources: Resources) {
//    private val initialSubjectList = getInitialSubjects()
//    private val subjectsLiveData = MutableLiveData(initialSubjectList)
    var wrongAnswerList = MutableLiveData<List<WrongAnswerListResponse>>()

    val wrongAnswerAPIService = RetrofitClient.create(WrongAnswerAPIService::class.java)
    val subjectAPIService = RetrofitClient.create(TMService::class.java)

//    fun fetchSubjectsByGrade(grade: Int): String? {
//        val call = subjectAPIService.getSubjectsByGrade(grade)
//        return try {
//            val response = call.execute()
//            if (response.isSuccessful) {
//                val subjectList = response.body()
//                subjectList?.let {
//                    for (subject in it) {
//                        Log.d("Subject", "ID: ${subject.subjectId}, Name: ${subject.subjectName}, Image: ${subject.img}")
//                        if (subject.subjectId == grade.toLong()) {
//                            return subject.subjectName
//                        }
//                    }
//                }
//            } else {
//                Log.e("API Error", "Error: ${response.code()}")
//            }
//            null
//        } catch (e: Exception) {
//            Log.e("Network Error", "Error: ${e.message}")
//            null
//        }
//    }

    fun fetchWrongAnswers(grade: Int, subjectId: Int, gradeString: String) {
        val call = wrongAnswerAPIService.getNotesByGradeSubject(grade, subjectId)
        call.enqueue(object : Callback<List<WrongAnswerListResponse>> {
            override fun onResponse(call: Call<List<WrongAnswerListResponse>>, response: Response<List<WrongAnswerListResponse>>) {
                if (response.isSuccessful) {
                    val notes: List<WrongAnswerListResponse>? = response.body()
                    notes?.let {
                        GlobalScope.launch(Dispatchers.Main) {
                            val updatedNotes = it.map { note ->
                                note.copy(grade = gradeString)
                            }
                            // 수정된 데이터 리스트를 postValue로 설정
                            wrongAnswerList.postValue(updatedNotes)

                            // 디버그 출력
                            for (note in updatedNotes) {
                                println("Note ID: ${note.noteId}, Title: ${note.title}, Subject: ${note.subjectId}")
                            }
                        }
                    }
                } else {
                    // 서버로부터 응답이 실패한 경우 처리
                    println("Failed to get notes. Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<WrongAnswerListResponse>>, t: Throwable) {
                // API 호출 실패 시 처리
                println("Failed to get notes. Error message: ${t.message}")
            }
        })
    }

//    fun addSubject(subject: Subject) {
//        val currentList = subjectsLiveData.value
//        if (currentList == null) {
//            subjectsLiveData.postValue(listOf(subject))
//        } else {
//            val updatedList = currentList.toMutableList() // 불변형 리스트로 변경
//            updatedList.add(subject) // 뒤에 나오도록  - 앞으로 나오도록 add(0, goal)
//            subjectsLiveData.postValue(updatedList) // 관찰자에게 변경 사항 전달
//        }
//    }
//
//    fun removeSubject(subject: Subject) {
//        val currentList = subjectsLiveData.value
//        if (currentList != null) {
//            val updatedList = currentList.toMutableList()
//            updatedList.remove(subject)
//            subjectsLiveData.postValue(updatedList)
//        }
//    }

    fun getNoteList(): MutableLiveData<List<WrongAnswerListResponse>> {
        return wrongAnswerList
    }

//    private fun getInitialSubjects() = listOf(
//        Subject(
//            id = 1,
//            name = "국어",
//            image = "book_red"
//        ),
//        Subject(
//            id = 2,
//            name = "수학",
//        ),
//        Subject(
//            id = 3,
//            name = "과학",
//        ),
//        Subject(
//            id = 4,
//            name = "역사",
//        ),
//        Subject(
//            id = 5,
//            name = "자바",
//        )
//    )
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