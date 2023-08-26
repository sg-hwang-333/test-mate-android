package kr.hs.emirim.evie.testmateloginpage

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.start_page)
        supportActionBar?.hide() // Android 앱의 액션바(작업 표시줄)를 숨기는 코드

        val i = Intent(this, LoginActivity::class.java)
        //Android 앱 구성 요소 간의 통신을 위해 사용되는 객체
        //액티비티 전환, 데이터 공유, 서비스 시작 등에 사용

        val handler = Handler()
        handler.postDelayed({
            startActivity(i)
            finish()
        }, 2000)


    }

}