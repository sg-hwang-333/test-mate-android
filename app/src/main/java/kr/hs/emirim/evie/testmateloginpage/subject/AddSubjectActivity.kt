package kr.hs.emirim.evie.testmateloginpage.subject

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.api.SubjectAPIService
import kr.hs.emirim.evie.testmateloginpage.comm.RetrofitClient
import kr.hs.emirim.evie.testmateloginpage.login.CurrentUser
import kr.hs.emirim.evie.testmateloginpage.subject.data.Subject
import kr.hs.emirim.evie.testmateloginpage.subject.data.SubjectResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

const val SUBJECT_NAME = "새로운 과목"
const val BOOK_TAG = "이미지 주소" // R.drawable.book_red

class AddSubjectActivity : AppCompatActivity() {
    private lateinit var addSubjectName: EditText
    lateinit var finishBtn : androidx.appcompat.widget.AppCompatImageButton
    lateinit var bookImg : ImageView
    var bookImgPath : String? = "book_red"

//    private val subjectViewModel by viewModels<SubjectViewModel> {
//        SubjectViewModelFactory(this)
//    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_subjects)
        supportActionBar?.hide()

//        val currentUser : UserDetailsResponse? = UserRepository.getUsersData();

        addSubjectName = findViewById(R.id.subjectName)

        finishBtn = findViewById(R.id.finish_edit_subject)
        finishBtn.setOnClickListener{
            finish()
        }

        findViewById<Button>(R.id.subjectAddBtn).setOnClickListener {
            val grade = CurrentUser.userDetails!!.grade
            val subjectName = addSubjectName.text.toString()
            Log.d("retrofit", grade.toString())
            Log.d("retrofit", subjectName)
            Log.d("retrofit", bookImgPath!!)

            if (subjectName.isNotEmpty()) {
                val newSubject = Subject(grade, subjectName, bookImgPath)
                addSubject(newSubject)
            } else {
                Log.d("AddSubjectActivity", "Subject name cannot be empty")
            }
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

//    private fun addSubject() {
//        val resultIntent = Intent()
//
//        if (addSubjectName.text.isNullOrEmpty()) {
//            setResult(Activity.RESULT_CANCELED, resultIntent)
//        } else {
//            val name = addSubjectName.text.toString()
//            resultIntent.putExtra(SUBJECT_NAME, name)
//            resultIntent.putExtra(BOOK_TAG, bookImgPath)
//
//            setResult(Activity.RESULT_OK, resultIntent)
//        }
//        finish()
//    }

    private fun addSubject(subject: Subject) {
        val subjectAPIService = RetrofitClient.create(SubjectAPIService::class.java, this)
        val call = subjectAPIService.createSubject(subject)
        call.enqueue(object : Callback<SubjectResponse> {
            override fun onResponse(call: Call<SubjectResponse>, response: Response<SubjectResponse>) {
                if (response.isSuccessful) {
                    val resultIntent = Intent()
                    resultIntent.putExtra(SUBJECT_NAME, subject.subjectName)
                    resultIntent.putExtra(BOOK_TAG, subject.img)
                    setResult(Activity.RESULT_OK, resultIntent)
                    finish()
                } else {
                    Log.d("AddSubjectActivity", "Failed to add subject. Error code: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SubjectResponse>, t: Throwable) {
                Log.d("AddSubjectActivity", "Failed to add subject. Error message: ${t.message}")
            }
        })
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