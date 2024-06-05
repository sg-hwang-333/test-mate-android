package kr.hs.emirim.evie.testmateloginpage.api

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kr.hs.emirim.evie.testmateloginpage.TMService
import kr.hs.emirim.evie.testmateloginpage.comm.RetrofitClient
import kr.hs.emirim.evie.testmateloginpage.userData.UserDetailsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.await
import retrofit2.converter.gson.GsonConverterFactory

//object UserInfo {
//    var userName: String? = null
//    var userGrade: Int? = null
//}

class UserRepository : AppCompatActivity() {
    companion object {
        val userAPIService = RetrofitClient.create(UserAPIService::class.java)

        //        fun getUsersData(){
//            val call = userAPIService.getUserDetails()
//            call.enqueue(
//                object : Callback<UserDetailsResponse> {
//                    override fun onResponse(
//                        call: Call<UserDetailsResponse>,
//                        response: Response<UserDetailsResponse>
//                    ) {
//                        if (response.isSuccessful) {
//                            response.body()
//
//                            // 응답에서 사용자 이름과 학년 추출
////                            UserInfo.userName = userDetails?.name
////                            UserInfo.userGrade = userDetails?.grade
////
////                            Log.d("username : ", UserInfo.userName.toString())
////                            Log.d("usergrade : ", UserInfo.userGrade.toString())
//
//                        } else {
//                            // API 호출이 실패한 경우에 대한 처리
//                        }
//                    }
//
//                    override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
//                        // 네트워크 오류 또는 서버 오류에 대한 처리
//                    }
//                })
//        }
        fun getUsersData(): UserDetailsResponse? {
            return try {
                // Retrofit의 비동기 호출을 suspend 함수로 wrapping
                val response = userAPIService.getUserDetails().execute()

                if (response.isSuccessful) {
                    response.body() // 성공적으로 UserDetailsResponse 객체를 반환
                } else {
                    null // 호출이 실패한 경우 null 반환
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null // 예외 발생 시 null 반환
            }
        }
    }
}
