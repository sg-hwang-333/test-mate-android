package kr.hs.emirim.evie.testmateloginpage.login

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.GsonBuilder
import kr.hs.emirim.evie.testmateloginpage.HomeActivity
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.SignUpActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LoginActivity : AppCompatActivity() {
    private lateinit var imageButton: ImageButton
    lateinit var editPass : EditText
    lateinit var editEmail : EditText
    private var isImageVisible = false
    lateinit var checkUser : TextView
    lateinit var signupBtn : TextView

    lateinit var submitBtn : android.widget.Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_page)
        supportActionBar?.hide()

        val gson = GsonBuilder()
            .setLenient()
            .create()

        var retrofit = Retrofit.Builder()
            .baseUrl("http://3.36.171.123:8086")
//            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        var loginService = retrofit.create(TMService::class.java)

        imageButton = findViewById(R.id.visible_btn)
        editPass = findViewById(R.id.edit_pw)
        editEmail = findViewById(R.id.edit_email)
        checkUser = findViewById(R.id.warning_message)

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

        submitBtn = findViewById(R.id.login_btn)

        submitBtn.setOnClickListener{
            var txtEmail = editEmail.text.toString()
            var txtPass = editPass.text.toString()

            val call = loginService.requestLogin(mapOf("email" to txtEmail, "password" to txtPass)) // 보내야 하는 정보를 가지고 있는 객체
            call.enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) { // response = 응답메세지(성공 or 실패)
                    val code = response.code()
                    if(code == 200) {
                        checkUser.visibility = View.INVISIBLE

                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent)

                        val result : LoginResponse = response.body() as LoginResponse
                        Log.d("mytag", response.code().toString() + "  " + result.message)
                    } else {
//                        Toast.makeText(this@LoginActivity, "로그인 실패 (아이디, 패스워드 확인 필요)", Toast.LENGTH_LONG).show()
                        checkUser.visibility = View.VISIBLE
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Log.d("mytag", "fail " + t.message)
                }

            })
        }

        signupBtn = findViewById(R.id.signup_btn)
        signupBtn.setOnClickListener {
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }
    }
}