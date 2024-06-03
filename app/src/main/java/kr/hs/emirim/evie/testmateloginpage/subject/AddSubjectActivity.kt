package kr.hs.emirim.evie.testmateloginpage.subject

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.databinding.ActivityEditSubjectsBinding

const val SUBJECT_NAME = "새로운 과목"
const val BOOK_TAG = "이미지 주소" // R.drawable.book_red

class AddSubjectActivity : AppCompatActivity() {
    private lateinit var addSubjectName: EditText
    lateinit var finishBtn : androidx.appcompat.widget.AppCompatImageButton
    lateinit var bookImg : ImageView
    var bookImgPath : String? = "book_red"

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_subjects)
        supportActionBar?.hide()

        addSubjectName = findViewById(R.id.subjectName)

        finishBtn = findViewById(R.id.finish_edit_subject)
        finishBtn.setOnClickListener{
            finish()
        }

        findViewById<Button>(R.id.subjectConfirmBtn).setOnClickListener {
            addSubject()
        }
//        addSubjectName = findViewById(R.id.subjectName)

        bookImg = findViewById(R.id.book_img)
        findViewById<ImageButton>(R.id.red).setOnClickListener {
            bookImg.setImageResource(R.drawable.book_red)
            bookImgPath = "book_red"
        }
        findViewById<ImageButton>(R.id.green).setOnClickListener {
            bookImg.setImageResource(R.drawable.book_green)
            bookImgPath = "book_green"
        }
        findViewById<ImageButton>(R.id.purple).setOnClickListener {
            bookImg.setImageResource(R.drawable.book_purple)
            bookImgPath = "book_purple"
        }
        findViewById<ImageButton>(R.id.blue).setOnClickListener {
            bookImg.setImageResource(R.drawable.book_blue)
            bookImgPath = "book_blue"
        }
        findViewById<ImageButton>(R.id.yellow).setOnClickListener {
            bookImg.setImageResource(R.drawable.book_yellow)
            bookImgPath = "book_yellow"
        }
        findViewById<ImageButton>(R.id.black).setOnClickListener {
            bookImg.setImageResource(R.drawable.book_black)
            bookImgPath = "book_black"
        }
    }

    private fun getImageResource(): Int? { // 이미지 리소스 가져
        return bookImg.tag as? Int
    }

    private fun addSubject() {
        val resultIntent = Intent()

        if (addSubjectName.text.isNullOrEmpty()) {
            setResult(Activity.RESULT_CANCELED, resultIntent)
        } else {
            val name = addSubjectName.text.toString()
            resultIntent.putExtra(SUBJECT_NAME, name)
            resultIntent.putExtra(BOOK_TAG, bookImgPath)

            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }
}

// 다이얼로그로 띄우는 코드
//class AddSubjectActivity : DialogFragment() {
//
//    private var _binding: ActivityEditSubjectsBinding? = null
//    private val binding get() = _binding!!
//
//    private lateinit var addSubjectName: EditText
//    lateinit var finishBtn : androidx.appcompat.widget.AppCompatImageButton
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        _binding = ActivityEditSubjectsBinding.inflate(inflater, container, false)
//        val view = binding.root
//        dialog?.window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
//        dialog?.setCancelable(true)
//        return view
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//    }
//}