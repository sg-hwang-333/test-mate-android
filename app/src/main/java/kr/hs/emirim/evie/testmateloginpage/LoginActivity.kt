package kr.hs.emirim.evie.testmateloginpage

import android.os.Bundle
import android.text.InputType
import android.view.MotionEvent
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var imageButton: ImageButton
    lateinit var editPass : EditText
    lateinit var editEmail : EditText
    private var isImageVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)


        imageButton = findViewById(R.id.visible_btn)
        editPass = findViewById(R.id.edit_pw)
        editEmail = findViewById(R.id.edit_email)

        imageButton.setOnClickListener {
            isImageVisible = !isImageVisible
            if (isImageVisible) {
                imageButton.setImageResource(R.drawable.invisible_icon)
                editPass.inputType = InputType.TYPE_CLASS_TEXT
            } else {
                imageButton.setImageResource(R.drawable.visible_icon)
                editPass.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            }
        }
    }

}