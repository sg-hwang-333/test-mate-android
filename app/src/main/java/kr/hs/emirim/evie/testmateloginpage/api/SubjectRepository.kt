package kr.hs.emirim.evie.testmateloginpage.api

import android.content.Context
import android.content.res.Resources
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kr.hs.emirim.evie.testmateloginpage.comm.RetrofitClient
import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject
import kr.hs.emirim.evie.testmateloginpage.subject.data.SubjectResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SubjectRepository(resources: Resources, context: Context) {
    var subjectListData = MutableLiveData<List<Subject>>()

    // Context를 사용하여 RetrofitClient를 생성
    val subjectAPIService : SubjectAPIService

    fun getSubjectList(): MutableLiveData<List<Subject>> {
        return subjectListData
    }

    init {
        subjectAPIService = RetrofitClient.create(SubjectAPIService::class.java, context)
    }

    fun postSubject(subject: Subject) {
        val call = subjectAPIService.postSubject(subject)
        call.enqueue(object : Callback<SubjectResponse> {
            override fun onResponse(call: Call<SubjectResponse>, response: Response<SubjectResponse>) {
                if (response.isSuccessful) {
                    println("Failed to get notes. Error code: ${response.code()}")
                } else {
                    println("Failed to get notes. Error code: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<SubjectResponse>, t: Throwable) {
                println("Failed to get notes. Error message: ${t.message}")
            }
        })
    }

    fun getSubjects(grade : Int) {
        val call = subjectAPIService.getSubjects(grade)
        call.enqueue(object : Callback<List<Subject>> {
            override fun onResponse(call: Call<List<Subject>>, response: Response<List<Subject>>) {
                if (response.isSuccessful) {
                    val subjectList = response.body()
                    subjectList?.let {
                        subjectListData.postValue(it) // 받은 과목 정보를 MutableLiveData에 저장
                    }
                } else {
                    Log.e("API Error", "Failed to fetch subject info. Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Subject>>, t: Throwable) {
                Log.e("API Error", "Failed to fetch subject info. Error message: ${t.message}")
            }
        })
    }

    companion object { // 데이터를 관리하고 제공하는 데 사용되는 클래스의 인스턴스를 만들지 않고도 호출하게 함
        private var INSTANCE: SubjectRepository? = null
        fun getDataSource(resources: Resources, context: Context): SubjectRepository {
            return synchronized(SubjectRepository::class) {
                val newInstance = INSTANCE ?: SubjectRepository(resources, context)
                INSTANCE = newInstance
                newInstance
            }
        }
    }
}