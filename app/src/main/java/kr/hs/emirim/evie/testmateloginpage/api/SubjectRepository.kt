package kr.hs.emirim.evie.testmateloginpage.api

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import kr.hs.emirim.evie.testmateloginpage.comm.RetrofitClient
import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject
import kr.hs.emirim.evie.testmateloginpage.subject.data.SubjectResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SubjectRepository(resources: Resources) {
    var subjectListData = MutableLiveData<List<Subject>>()

    // Context를 사용하여 RetrofitClient를 생성
    val subjectAPIService = RetrofitClient.create(SubjectAPIService::class.java)

    fun getSubjectList(): MutableLiveData<List<Subject>> {
        return subjectListData
    }

    fun fetchSubject(subject: Subject) {
        val call = subjectAPIService.createSubject(subject)
        call.enqueue(object : Callback<SubjectResponse> {
            override fun onResponse(call: Call<SubjectResponse>, response: Response<SubjectResponse>) {
                if (response.isSuccessful) {
                    val newSubject: Subject? = subject
                    newSubject?.let {
                        val updatedList = subjectListData.value.orEmpty().toMutableList()
                        updatedList.add(it)
                        subjectListData.postValue(updatedList)
                    }
                } else {
                    println("Failed to get notes. Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SubjectResponse>, t: Throwable) {
                println("Failed to get notes. Error message: ${t.message}")
            }
        })
    }

    //    fun showSubject(grade : Int) {
//        val call = subjectAPIService.getSubjects(grade)
//        call.enqueue(object : Callback<List<Subject>> {
//            override fun onResponse(call: Call<List<Subject>>, response: Response<List<Subject>>) {
//                if (response.isSuccessful) {
//                    val subjects: List<Subject>? = response.body()
//                    subjectList.postValue(subjects)
//                } else {
//                    // 서버로부터 응답이 실패한 경우 처리
//                    println("Failed to get notes. Error code: ${response.code()}")
//                }
//            }
//
//            override fun onFailure(call: Call<List<WrongAnswerListResponse>>, t: Throwable) {
//                // API 호출 실패 시 처리
//                println("Failed to get notes. Error message: ${t.message}")
//            }
//        })
//
//    }
    companion object { // 데이터를 관리하고 제공하는 데 사용되는 클래스의 인스턴스를 만들지 않고도 호출하게 함
        private var INSTANCE: SubjectRepository? = null
        fun getDataSource(resources: Resources): SubjectRepository {
            return synchronized(SubjectRepository::class) {
                val newInstance = INSTANCE ?: SubjectRepository(resources)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}