package kr.hs.emirim.evie.testmateloginpage

import android.content.DialogInterface
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


class SignUpActivity : AppCompatActivity() {

    lateinit var btnJoin : android.widget.Button
    lateinit var btnGradeDialog : android.widget.Button

    var arrGrade = arrayOf<String>("중학교 1학년", "중학교 2학년", "중학교 3학년", "고등학교 1학년", "고등학교 2학년", "고등학교 3학년")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_page)

        btnJoin = findViewById(R.id.btn_signup)
        btnGradeDialog = findViewById(R.id.signup_grade)

        btnGradeDialog.setOnClickListener {
            var dlg = AlertDialog.Builder(this@SignUpActivity, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
            dlg.setTitle("학년정보")
            dlg.setItems(arrGrade) {dialog, index ->
                btnGradeDialog.text = arrGrade[index]
            }
            dlg.setNegativeButton("닫기") { dialog, which ->
                // 버튼 클릭 이벤트 처리 코드
                dialog.dismiss() // 다이얼로그 닫기
            }
            dlg.create().show()
        }

    }

}