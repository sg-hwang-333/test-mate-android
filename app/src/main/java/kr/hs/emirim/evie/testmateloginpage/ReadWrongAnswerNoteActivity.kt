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
import kr.hs.emirim.evie.testmateloginpage.comm.RetrofitClient
import kr.hs.emirim.evie.testmateloginpage.home.HomeActivity
import kr.hs.emirim.evie.testmateloginpage.login.LoginActivity
import kr.hs.emirim.evie.testmateloginpage.signup.SignUpRequest
import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data.WrongAnswerNote

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReadWrongAnswerNoteActivity : AppCompatActivity() {
    private lateinit var before: ImageView
    private lateinit var noteTitle: TextView
    private lateinit var noteGrade: TextView
    private lateinit var noteContent: TextView
    private lateinit var mistakeBtn: Button
    private lateinit var timeoutBtn: Button
    private lateinit var lackConceptBtn: Button
    private lateinit var scopeBtn1: Button
    private lateinit var scopeBtn2: Button
    private lateinit var scopeBtn3: Button
    private lateinit var scopeBtn4: Button
    private lateinit var deleteBtn: Button
    private lateinit var editBtn: Button

    val gradeStringList = arrayOf("중학교 1학년", "중학교 2학년", "중학교 3학년", "고등학교 1학년", "고등학교 2학년", "고등학교 3학년")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wrong_answer_note_detail)

        val selectedNote = intent.getSerializableExtra("selectedNote") as? WrongAnswerNote
        val selectedPosition = intent.getIntExtra("selectedPosition", -1)

        initializeViews() // view findViewById

        before.setOnClickListener {
            // Handle back button click
            finish()
        }

        // TODO : selectedNote의 noteId로 api 연동
        selectedNote?.let {
            noteTitle.text = it.title
            noteGrade.text = gradeStringList[it.grade - 1]
            noteContent.text = it.styles

            reasonBtns(selectedNote.reason)
            rangeBtns(selectedNote.range)

        }





    }

    fun initializeViews() {
        before = findViewById(R.id.before)
        noteTitle = findViewById(R.id.note_title)
        noteGrade = findViewById(R.id.note_grade)
        noteContent = findViewById(R.id.note_content)
        mistakeBtn = findViewById(R.id.mistake_btn)
        timeoutBtn = findViewById(R.id.timeout_btn)
        lackConceptBtn = findViewById(R.id.lack_concept_btn)
        scopeBtn1 = findViewById(R.id.scope_btn1)
        scopeBtn2 = findViewById(R.id.scope_btn2)
        scopeBtn3 = findViewById(R.id.scope_btn3)
        scopeBtn4 = findViewById(R.id.scope_btn4)
        deleteBtn = findViewById(R.id.deleteBtn)
        editBtn = findViewById(R.id.editBtn)
    }

    fun reasonBtns(reason : String?) {
        if (mistakeBtn.text == reason) {
            // 모든 버튼의 텍스트가 같을 때
            mistakeBtn.setBackgroundResource(R.drawable.bg_green_view)
            mistakeBtn.setTextColor(ContextCompat.getColor(this, R.color.white))
            timeoutBtn.setBackgroundResource(R.drawable.bg_white_view)
            timeoutBtn.setTextColor(ContextCompat.getColor(this, R.color.black_300))
            lackConceptBtn.setBackgroundResource(R.drawable.bg_white_view)
            lackConceptBtn.setTextColor(ContextCompat.getColor(this, R.color.black_300))
        } else if (timeoutBtn.text == reason) {
            // mistakeBtn과 timeoutBtn의 텍스트가 같을 때
            mistakeBtn.setBackgroundResource(R.drawable.bg_white_view)
            mistakeBtn.setTextColor(ContextCompat.getColor(this, R.color.black_300))
            timeoutBtn.setBackgroundResource(R.drawable.bg_green_view)
            timeoutBtn.setTextColor(ContextCompat.getColor(this, R.color.white))
            lackConceptBtn.setBackgroundResource(R.drawable.bg_white_view)
            lackConceptBtn.setTextColor(ContextCompat.getColor(this, R.color.black_300))
        } else {
            // 모든 버튼의 텍스트가 다를 때 기본 스타일 적용
            mistakeBtn.setBackgroundResource(R.drawable.bg_white_view)
            mistakeBtn.setTextColor(ContextCompat.getColor(this, R.color.black_300))
            timeoutBtn.setBackgroundResource(R.drawable.bg_white_view)
            timeoutBtn.setTextColor(ContextCompat.getColor(this, R.color.black_300))
            lackConceptBtn.setBackgroundResource(R.drawable.bg_green_view)
            lackConceptBtn.setTextColor(ContextCompat.getColor(this, R.color.white))
        }
    }

    fun rangeBtns(range : String?) {
        if (scopeBtn1.text == range) {
            // 모든 버튼의 텍스트가 같을 때
            scopeBtn1.setBackgroundResource(R.drawable.bg_green_view)
            scopeBtn1.setTextColor(ContextCompat.getColor(this, R.color.white))
            scopeBtn2.setBackgroundResource(R.drawable.bg_white_view)
            scopeBtn2.setTextColor(ContextCompat.getColor(this, R.color.black_300))
            scopeBtn3.setBackgroundResource(R.drawable.bg_white_view)
            scopeBtn3.setTextColor(ContextCompat.getColor(this, R.color.black_300))
            scopeBtn4.setBackgroundResource(R.drawable.bg_white_view)
            scopeBtn4.setTextColor(ContextCompat.getColor(this, R.color.black_300))
        } else if (scopeBtn2.text == range) {
            // mistakeBtn과 timeoutBtn의 텍스트가 같을 때
            scopeBtn1.setBackgroundResource(R.drawable.bg_white_view)
            scopeBtn1.setTextColor(ContextCompat.getColor(this, R.color.black_300))
            scopeBtn2.setBackgroundResource(R.drawable.bg_green_view)
            scopeBtn2.setTextColor(ContextCompat.getColor(this, R.color.white))
            scopeBtn3.setBackgroundResource(R.drawable.bg_white_view)
            scopeBtn3.setTextColor(ContextCompat.getColor(this, R.color.black_300))
            scopeBtn4.setBackgroundResource(R.drawable.bg_white_view)
            scopeBtn4.setTextColor(ContextCompat.getColor(this, R.color.black_300))
        } else if (scopeBtn3.text == range) {
            // mistakeBtn과 timeoutBtn의 텍스트가 같을 때
            scopeBtn1.setBackgroundResource(R.drawable.bg_white_view)
            scopeBtn1.setTextColor(ContextCompat.getColor(this, R.color.black_300))
            scopeBtn2.setBackgroundResource(R.drawable.bg_white_view)
            scopeBtn2.setTextColor(ContextCompat.getColor(this, R.color.black_300))
            scopeBtn3.setBackgroundResource(R.drawable.bg_green_view)
            scopeBtn3.setTextColor(ContextCompat.getColor(this, R.color.white))
            scopeBtn4.setBackgroundResource(R.drawable.bg_white_view)
            scopeBtn4.setTextColor(ContextCompat.getColor(this, R.color.black_300))
        } else {
            // 모든 버튼의 텍스트가 다를 때 기본 스타일 적용
            scopeBtn1.setBackgroundResource(R.drawable.bg_white_view)
            scopeBtn1.setTextColor(ContextCompat.getColor(this, R.color.black_300))
            scopeBtn2.setBackgroundResource(R.drawable.bg_white_view)
            scopeBtn2.setTextColor(ContextCompat.getColor(this, R.color.black_300))
            scopeBtn3.setBackgroundResource(R.drawable.bg_white_view)
            scopeBtn3.setTextColor(ContextCompat.getColor(this, R.color.black_300))
            scopeBtn4.setBackgroundResource(R.drawable.bg_green_view)
            scopeBtn4.setTextColor(ContextCompat.getColor(this, R.color.white))
        }
    }

}