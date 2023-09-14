package kr.hs.emirim.evie.testmateloginpage.subject

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kr.hs.emirim.evie.testmateloginpage.R

const val SUBJECT_NAME = "새로운 목표"

class AddSubjectActivity : AppCompatActivity() {
    private lateinit var addSubjectName: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.add_flower_layout) //  TODO : 과목 추가 화면
//
//        findViewById<Button>(R.id.done_button).setOnClickListener {
//           addFlower()
//        }
//        addSubjectName = findViewById(R.id.add_flower_name)
    }

    private fun addSubject() {
        val resultIntent = Intent()

//        if (addSubjectName.text.isNullOrEmpty()) {
//            setResult(Activity.RESULT_CANCELED, resultIntent)
//        } else {
//            val name = addSubjectName.text.toString()
//            resultIntent.putExtra(SUBJECT_NAME, name)
//            setResult(Activity.RESULT_OK, resultIntent)
//        }
//        finish()
    }
}