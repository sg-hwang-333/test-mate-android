package kr.hs.emirim.evie.testmateloginpage

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kr.hs.emirim.evie.testmateloginpage.login.LoginActivity

class FindIdCheckActivity : AppCompatActivity() {
    lateinit var goLoginBtn : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_id_check)

        goLoginBtn = findViewById(R.id.id_goLogin)

        goLoginBtn.setOnClickListener {
            val intent = Intent(this@FindIdCheckActivity, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }
    }
}