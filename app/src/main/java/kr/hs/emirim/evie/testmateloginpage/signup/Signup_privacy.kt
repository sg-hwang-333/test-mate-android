package kr.hs.emirim.evie.testmateloginpage.signup

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.login.LoginActivity

class Signup_privacy : AppCompatActivity() {

    lateinit var cancelButton: ImageView
    lateinit var collectionShow: TextView
    lateinit var deletionShow: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_page_privacy)
        supportActionBar?.hide()

        cancelButton = findViewById(R.id.cancel_button)
        collectionShow = findViewById(R.id.collection)
        deletionShow = findViewById(R.id.deletion)

        cancelButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
        collectionShow.setOnClickListener {
            val intent = Intent(this, terms_privacy_collection::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
        deletionShow.setOnClickListener {
            val intent = Intent(this, terms_privacy_deletion::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }
}