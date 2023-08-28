package kr.hs.emirim.evie.testmateloginpage.goalList

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText

const val GOALDESCRIPTION = "학습목표를 입력하세요"
const val GOALCECKED = false

class AddGoalActivity : AppCompatActivity() {
    private lateinit var addGoalDescription: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addGoal()
    }

    /* The onClick action for the done button. Closes the activity and returns the new flower name
    and description as part of the intent. If the name or description are missing, the result is set
    to cancelled. */

    private fun addGoal() {
        val resultIntent = Intent()

//        if (addGoalName.text.isNullOrEmpty() || addGoalDescription.text.isNullOrEmpty()) {
//            setResult(Activity.RESULT_CANCELED, resultIntent)
//        } else {
//            val name = addGoalName.text.toString()
//            val description = addGoalDescription.text.toString()
//            resultIntent.putExtra(FLOWER_NAME, name)
//            resultIntent.putExtra(FLOWER_DESCRIPTION, description)
//            setResult(Activity.RESULT_OK, resultIntent)
//        }
        finish()
    }
}