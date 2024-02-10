package kr.hs.emirim.evie.testmateloginpage.signup

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.login.LoginActivity

class signup_privacy : AppCompatActivity() {
    lateinit var cancelButton : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_page_privacy)
        supportActionBar?.hide()

        cancelButton = findViewById(R.id.cancel_button)

        cancelButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }

    }
}