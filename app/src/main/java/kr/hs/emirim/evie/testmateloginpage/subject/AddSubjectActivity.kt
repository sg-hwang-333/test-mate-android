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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.google.android.material.textfield.TextInputEditText
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.databinding.ActivityEditSubjectsBinding

const val SUBJECT_NAME = "새로운 과목"

class AddSubjectActivity : AppCompatActivity() {
    private lateinit var addSubjectName: EditText
    lateinit var finishBtn : androidx.appcompat.widget.AppCompatImageButton

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
}
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