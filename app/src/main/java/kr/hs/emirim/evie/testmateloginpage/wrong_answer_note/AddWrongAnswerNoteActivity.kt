package kr.hs.emirim.evie.testmateloginpage.wrong_answer_note

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kr.hs.emirim.evie.testmateloginpage.calendar.Calendar
import kr.hs.emirim.evie.testmateloginpage.R
import kr.hs.emirim.evie.testmateloginpage.subject.GoalMainListActivity
import kr.hs.emirim.evie.testmateloginpage.home.HomeActivity

class AddWrongAnswerNoteActivity : AppCompatActivity() {

    lateinit var btnGradeDialog: Button
    lateinit var addBtn : android.widget.Button

    lateinit var navHome : ImageButton
    lateinit var navWrong : ImageButton
    lateinit var navGoal : ImageButton
    lateinit var navCal : ImageButton

    lateinit var pre: SharedPreferences

    var arrGrade = arrayOf("중학교 1학년", "중학교 2학년", "중학교 3학년", "고등학교 1학년", "고등학교 2학년", "고등학교 3학년")

    private lateinit var selectedReason: String
    private lateinit var selectedScope: String

    private lateinit var uploadLayout: LinearLayout
    private lateinit var imageLayout: LinearLayout
    private lateinit var imageView: ImageView
    private lateinit var uploadBtnFirstLayout: Button
    private lateinit var uploadImgBtnSecondLayout: Button

    private val PICK_IMAGE_REQUEST = 1

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wrong_answer_note_add)

        selectedReason = "실수"
        selectedScope = "추가자료"

        var beforeBtn = findViewById<ImageView>(R.id.before)
        beforeBtn.setOnClickListener{
            finish()
            overridePendingTransition(0, 0)
        }

        // spinner
        btnGradeDialog = findViewById(R.id.signup_grade)
        pre = getSharedPreferences("UserInfo", MODE_PRIVATE)
        val editor = pre.edit()

        btnGradeDialog.setOnClickListener {
            val dlg = AlertDialog.Builder(this@AddWrongAnswerNoteActivity, android.R.style.Theme_DeviceDefault_Light_Dialog_Alert)
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

        // 오답이유 버튼들
        val reasonButtons = listOf(
            findViewById<Button>(R.id.mistake_btn),
            findViewById<Button>(R.id.timeout_btn),
            findViewById<Button>(R.id.lack_concept_btn)
        )

        // 문제 범위 버튼들
        val scopeButtons = listOf(
            findViewById<Button>(R.id.scope_btn1),
            findViewById<Button>(R.id.scope_btn2),
            findViewById<Button>(R.id.scope_btn3),
            findViewById<Button>(R.id.scope_btn4)
        )

        initializeButtonListeners(reasonButtons) { selectedButton ->
            selectedReason = selectedButton.text.toString()
            Log.d("wrongAnswer", selectedReason)
        }

        initializeButtonListeners(scopeButtons) { selectedButton ->
            selectedScope = selectedButton.text.toString()
        }

        addBtn = findViewById(R.id.addBtn)
        addBtn.setOnClickListener {
//            WrongList.visibility = View.VISIBLE
            onBackPressed()
        }

        uploadLayout = findViewById(R.id.upload_layout)
        imageLayout = findViewById(R.id.image_layout)
        imageView = findViewById(R.id.imageView)
        uploadBtnFirstLayout = findViewById(R.id.upload_btn_first_layout)
        uploadImgBtnSecondLayout = findViewById(R.id.upload_img_btn_second_layout)

        uploadBtnFirstLayout.setOnClickListener {
            openFileChooser()
        }

        uploadImgBtnSecondLayout.setOnClickListener {
        }

// navgation
        navHome = findViewById(R.id.nav_home)
        navWrong = findViewById(R.id.nav_wrong)
        navGoal = findViewById(R.id.nav_goal)
        navCal = findViewById(R.id.nav_cal)

        navHome.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }
        navWrong.setOnClickListener {
            val intent = Intent(this, WrongAnswerListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }
        navGoal.setOnClickListener {
            val intent = Intent(this, GoalMainListActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }
        navCal.setOnClickListener {
            val intent = Intent(this, Calendar::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            startActivity(intent)
        }
    }

    private fun handleReasonButtonClick(selectedButton: Button, buttons: List<Button>) {
        for (button in buttons) {
            if (button == selectedButton) {
                button.setBackgroundResource(R.drawable.bg_green_view)
                button.setTextColor(ContextCompat.getColor(this, R.color.white))
                selectedReason = button.text.toString()
                Log.d("wrongAnswer", selectedReason!!)
            } else {
                button.setBackgroundResource(R.drawable.bg_white_view)
                button.setTextColor(ContextCompat.getColor(this, R.color.black_300))
            }
        }
    }

    private fun initializeButtonListeners(buttons: List<Button>, onSelect: (Button) -> Unit) {
        buttons.forEach { button ->
            button.setOnClickListener {
                handleButtonClick(button, buttons, onSelect)
            }
        }
    }

    private fun handleButtonClick(selectedButton: Button, buttons: List<Button>, onSelect: (Button) -> Unit) {
        buttons.forEach { button ->
            if (button == selectedButton) {
                button.setBackgroundResource(R.drawable.bg_green_view)
                button.setTextColor(ContextCompat.getColor(this, R.color.white))
                onSelect(button)
            } else {
                button.setBackgroundResource(R.drawable.bg_white_view)
                button.setTextColor(ContextCompat.getColor(this, R.color.black_300))
            }
        }
    }
    private fun openFileChooser() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "이미지 선택"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            val imageUri = data.data

            try {
                val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
                imageView.setImageBitmap(bitmap)

                // 이미지를 보여주는 LinearLayout 표시 및 업로드 LinearLayout 숨김
                imageLayout.visibility = View.VISIBLE
                uploadLayout.visibility = View.GONE

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}