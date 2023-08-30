package kr.hs.emirim.evie.testmateloginpage.goalList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import kr.hs.emirim.evie.testmateloginpage.R

const val GOALDESCRIPTION = "새로운 목표"
const val GOALCECKED = false

class AddGoalActivity : AppCompatActivity() {
    private lateinit var addGoalDescription: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        findViewById<Button>(R.id.buttonModify).setOnClickListener {
//            addGoal()
//        }

        addGoalDescription = findViewById(R.id.goal_description)

    }

    private fun addGoal() {
        // 목표 받음
        val description = addGoalDescription.text.toString()
    }
}