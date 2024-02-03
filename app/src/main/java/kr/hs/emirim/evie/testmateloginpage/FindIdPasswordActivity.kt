package kr.hs.emirim.evie.testmateloginpage

import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.content.ContextCompat

class FindIdPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_id_password)

        val btnFindId: Button = findViewById(R.id.btn_find_id)
        val btnFindPw: Button = findViewById(R.id.btn_find_pw)

        // 초기 상태 설정 (아이디 찾기 활성화)
        setButtonBackground(btnFindId, true)
        setButtonBackground(btnFindPw, false)

        // 버튼1 클릭 시
        btnFindId.setOnClickListener {
            setButtonBackground(btnFindId, true)
            setButtonBackground(btnFindPw, false)
        }

        // 버튼2 클릭 시
        btnFindPw.setOnClickListener {
            setButtonBackground(btnFindId, false)
            setButtonBackground(btnFindPw, true)
        }
    }

    private fun setButtonBackground(button: Button, isSelected: Boolean) {
        val drawableResId = if (isSelected) R.drawable.btn_find_id_password else R.drawable.btn_find_id_password_none
        val drawable = ContextCompat.getDrawable(this, drawableResId)
        button.background = drawable
    }
}