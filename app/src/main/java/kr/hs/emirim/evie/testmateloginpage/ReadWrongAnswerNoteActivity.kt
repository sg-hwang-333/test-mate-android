package kr.hs.emirim.evie.testmateloginpage

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kr.hs.emirim.evie.testmateloginpage.api.WrongAnswerRepository
import kr.hs.emirim.evie.testmateloginpage.wrong_answer_note.data.WrongAnswerNoteResponse

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

    private val wrongAnswerRepository by lazy {
        WrongAnswerRepository.getDataSource(resources)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wrong_answer_note_detail)

        val selectedNote = intent.getSerializableExtra("selectedNote") as? WrongAnswerNoteResponse
        val selectedPosition = intent.getIntExtra("selectedPosition", -1)

        initializeViews() // view findViewById

        before.setOnClickListener {
            // Handle back button click
            finish()
        }

        // 오답노트 detail api 연동
        selectedNote?.let {
            lifecycleScope.launch {
                val noteDetail = wrongAnswerRepository.getNoteDetail(selectedNote.noteId)
                noteDetail?.let {
                    noteTitle.text = it.title
                    noteGrade.text = gradeStringList[it.grade - 1]
                    noteContent.text = it.styles

                    reasonBtns(it.reason)
                    rangeBtns(it.range)
                }
            }
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