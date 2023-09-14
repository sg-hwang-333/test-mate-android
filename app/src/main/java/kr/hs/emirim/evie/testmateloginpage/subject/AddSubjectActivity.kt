package kr.hs.emirim.evie.testmateloginpage.subject

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kr.hs.emirim.evie.testmateloginpage.R

const val SUBJECT_NAME = "새로운 과목"

class AddSubjectActivity : AppCompatActivity() {
    private lateinit var addSubjectName: EditText
    lateinit var finishBtn : androidx.appcompat.widget.AppCompatImageButton

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_subjects) //  TODO : 과목 추가 화면
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
    }

    private fun addSubject() {
        val resultIntent = Intent()

        if (addSubjectName.text.isNullOrEmpty()) {
            setResult(Activity.RESULT_CANCELED, resultIntent)
        } else {
            val name = addSubjectName.text.toString()
            resultIntent.putExtra(SUBJECT_NAME, name)
            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        /* Inserts flower into viewModel. */
//        if (resultCode == Activity.RESULT_OK) {
//            intentData?.let { data ->
//                val subjectName = data.getStringExtra(SUBJECT_NAME)
//
//                SubjectsListVIewModel.insertSubject(subjectName) ///////////////////////////////////////// insertFlower
//            }
//        }
    }
}