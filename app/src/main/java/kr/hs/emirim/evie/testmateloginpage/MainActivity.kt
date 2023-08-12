package kr.hs.emirim.evie.testmateloginpage

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var imageButton: ImageButton
    private var isImageVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.alarm_page)

        imageButton = findViewById(R.id.password_btn)
        imageButton.setOnClickListener {
            isImageVisible = !isImageVisible
            if (isImageVisible) {
                imageButton.setImageResource(R.drawable.visible_icon)
            } else {
                imageButton.setImageResource(R.drawable.invisible_icon)
            }
        }
    }

}