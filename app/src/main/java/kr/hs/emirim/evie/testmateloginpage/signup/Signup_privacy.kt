package kr.hs.emirim.evie.testmateloginpage.signup

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatCheckBox
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.login.LoginActivity
import kr.hs.emirim.evie.testmateloginpage.SignUpActivity

class Signup_privacy : AppCompatActivity() {

    lateinit var cancelButton: ImageView
    lateinit var collectionShow: TextView
    lateinit var deletionShow: TextView

    lateinit var signupCheckedPrivacyCollection: AppCompatCheckBox
    lateinit var signupCheckedPrivacyDeletion: AppCompatCheckBox
    lateinit var signupCheckedAlarm: AppCompatCheckBox
    lateinit var signupChecked:AppCompatCheckBox
    lateinit var nextButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_page_privacy)
        supportActionBar?.hide()

        signupCheckedPrivacyCollection = findViewById(R.id.signup_checked_privacy_collection)
        signupCheckedPrivacyDeletion = findViewById(R.id.signup_checked_privacy_deletion)
        nextButton = findViewById(R.id.next_button)
        signupChecked = findViewById(R.id.signup_checked)
        signupCheckedAlarm = findViewById(R.id.signup_checked_alarm)

        cancelButton = findViewById(R.id.cancel_button)
        collectionShow = findViewById(R.id.collection)
        deletionShow = findViewById(R.id.deletion)


        signupChecked.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                signupCheckedPrivacyCollection.isChecked = true
                signupCheckedPrivacyDeletion.isChecked = true
                signupCheckedAlarm.isChecked = true
            } else {
                signupCheckedPrivacyCollection.isChecked = false
                signupCheckedPrivacyDeletion.isChecked = false
                signupCheckedAlarm.isChecked = false
            }
        }

        val checkAllChecked = {
            signupChecked.isChecked =
                signupCheckedPrivacyCollection.isChecked &&
                        signupCheckedPrivacyDeletion.isChecked &&
                        signupCheckedAlarm.isChecked

            val allChecked = signupCheckedPrivacyCollection.isChecked && signupCheckedPrivacyDeletion.isChecked
            if (allChecked) {
                nextButton.isEnabled = true
                val drawable = DrawableCompat.wrap(nextButton.background)
                DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.green_500))
            } else {
                nextButton.isEnabled = false
                val drawable = DrawableCompat.wrap(nextButton.background)
                DrawableCompat.setTint(drawable, ContextCompat.getColor(this, R.color.green_300))
            }
        }


        signupCheckedPrivacyCollection.setOnCheckedChangeListener { _, _ -> checkAllChecked() }
        signupCheckedPrivacyDeletion.setOnCheckedChangeListener { _, _ -> checkAllChecked() }
        signupCheckedAlarm.setOnCheckedChangeListener { _, _ -> checkAllChecked() }

        checkAllChecked()

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
        nextButton.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }
}