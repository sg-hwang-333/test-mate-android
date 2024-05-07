package kr.hs.emirim.evie.testmateloginpage

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kr.hs.emirim.evie.testmateloginpage.home.HomeActivity
import kr.hs.emirim.evie.testmateloginpage.login.LoginActivity
import kr.hs.emirim.evie.testmateloginpage.signup.SignUpRequest

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SignUpActivity : AppCompatActivity() {

    lateinit var btnJoin: Button
    lateinit var btnGradeDialog: Button
    lateinit var checkDuplication: Button
    lateinit var beforeBtn: ImageView
    lateinit var textViewDuplicateCheck: TextView
    lateinit var textUserId: EditText
    lateinit var textName: EditText
    lateinit var textEmailF: EditText
    lateinit var textEmailL: EditText
    lateinit var textPassword: EditText

    lateinit var pre: SharedPreferences

    var arrGrade = arrayOf("중학교 1학년", "중학교 2학년", "중학교 3학년", "고등학교 1학년", "고등학교 2학년", "고등학교 3학년")

//   TODO : 유효성 검사와 값에 따른 색 변경 추가하기
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup_page)

        btnJoin = findViewById(R.id.btn_signup)
        btnGradeDialog = findViewById(R.id.signup_grade)
        checkDuplication = findViewById(R.id.btn_check)
        beforeBtn = findViewById(R.id.backBtn)
        textViewDuplicateCheck = findViewById(R.id.textViewDuplicateCheck)
        textUserId = findViewById(R.id.signup_id)
        textName = findViewById(R.id.signup_name)
        textEmailF = findViewById(R.id.signup_email_first)
        textEmailL = findViewById(R.id.signup_email_second)
        textPassword = findViewById(R.id.signup_password)

        beforeBtn.setOnClickListener {
            onBackPressed()
        }

        pre = getSharedPreferences("UserInfo", MODE_PRIVATE)
        val editor = pre.edit()

        btnGradeDialog.setOnClickListener {
            val dlg = AlertDialog.Builder(this@SignUpActivity, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
            dlg.setTitle("학년정보")
            dlg.setItems(arrGrade) { dialog, index ->
                btnGradeDialog.text = arrGrade[index]
                editor.putString("usergrade", arrGrade[index])
                editor.apply()
            }
            dlg.setNegativeButton("닫기") { dialog, which ->
                dialog.dismiss()
            }
            dlg.create().show()
        }

        checkDuplication.setOnClickListener {
            val userId = textUserId.text.toString()

            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.1.247:8086")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(TMService::class.java)

            val call = service.checkUserId(userId)
            call.enqueue(object : Callback<UserCheckResponse> {
                override fun onResponse(call: Call<UserCheckResponse>, response: Response<UserCheckResponse>) {
                    if (response.isSuccessful) {
                        val userCheckResponse = response.body()
                        if (userCheckResponse?.message == "User with the same user ID already exists") {
                            // 아이디 중복
                            textUserId.setBackgroundResource(R.drawable.bg_red_view) // 테두리를 빨간색으로 변경
                            textViewDuplicateCheck.visibility = View.VISIBLE // 텍스트뷰를 보이게 설정
                            Toast.makeText(this@SignUpActivity, "아이디가 중복됩니다.", Toast.LENGTH_SHORT).show()
                        } else {
                            // 아이디 중복이 아님
                            textUserId.setBackgroundResource(R.drawable.bg_green_stroke_view) // 테두리를 초록색으로 변경
                            textViewDuplicateCheck.visibility = View.GONE // 텍스트뷰를 숨김
                            Toast.makeText(this@SignUpActivity, "사용 가능한 아이디입니다.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@SignUpActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<UserCheckResponse>, t: Throwable) {
                    Toast.makeText(this@SignUpActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
                }
            })
        }



        btnJoin.setOnClickListener {
            val userId = textUserId.text.toString()
            val name = textName.text.toString()
            val email = textEmailF.text.toString() + textEmailL.text.toString()
            val password = textPassword.text.toString()
            val selectedGradeIndex = arrGrade.indexOf(btnGradeDialog.text.toString()) + 1

            val signUpReq = SignUpRequest(userId, name, email, selectedGradeIndex, password)

            val retrofit = Retrofit.Builder()
                .baseUrl("http://192.168.1.247:8086")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(TMService::class.java)

            val call = service.signUp(signUpReq)
            call.enqueue(object : Callback<SignUpResponse> {
                override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
                    if (response.isSuccessful) {
                        val signUpResponse = response.body()
                        if (signUpResponse?.message == "Successfully signed up.") {
                            Toast.makeText(this@SignUpActivity, "회원가입 성공", Toast.LENGTH_SHORT).show()
                            Log.d("signuptag", response.toString())
                            // 회원가입 성공 후 로그인 화면으로 이동
                            val intent = Intent(this@SignUpActivity, LoginActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@SignUpActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@SignUpActivity, "회원가입 실패", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
                    Toast.makeText(this@SignUpActivity, "네트워크 오류", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}
