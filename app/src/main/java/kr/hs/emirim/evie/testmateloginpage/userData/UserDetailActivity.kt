package kr.hs.emirim.evie.testmateloginpage.userData
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.TMService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object UserInfo {
    var userName: String? = null
    var userGrade: Int? = null
}

class UserDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_user_detail)

        // Retrofit 객체 생성
        val retrofit = Retrofit.Builder()
            .baseUrl("http://172.22.64.1:8086") // API의 기본 URL 설정
            .addConverterFactory(GsonConverterFactory.create()) // JSON 파싱을 위한 컨버터 설정
            .build()

        // API 서비스 인터페이스 생성
        val apiService = retrofit.create(TMService::class.java)

        // API 호출
        val call = apiService.getUserDetails()

        call.enqueue(object : Callback<UserDetailsResponse> {
            override fun onResponse(call: Call<UserDetailsResponse>, response: Response<UserDetailsResponse>) {
                if (response.isSuccessful) {
                    val userDetails = response.body()

                    // 응답에서 사용자 이름과 학년 추출
                    UserInfo.userName = userDetails?.name
                    UserInfo.userGrade = userDetails?.grade

                    Log.d("username : ", UserInfo.userName.toString())
                    Log.d("usergrade : ", UserInfo.userGrade.toString())

                    // 추출한 정보를 필요한 곳에 저장 또는 사용
                } else {
                    // API 호출이 실패한 경우에 대한 처리
                }
            }

            override fun onFailure(call: Call<UserDetailsResponse>, t: Throwable) {
                // 네트워크 오류 또는 서버 오류에 대한 처리
            }
        })
    }
}
