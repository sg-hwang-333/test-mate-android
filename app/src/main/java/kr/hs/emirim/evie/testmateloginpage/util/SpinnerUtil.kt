package kr.hs.emirim.evie.testmateloginpage.util

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import kr.hs.emirim.evie.testmateloginpage.R

class SpinnerUtil {
    companion object {
        fun gradeSpinner(context: Context, spinnerId: Int): Spinner {
//            val pre = context.getSharedPreferences("UserInfo", Context.MODE_PRIVATE)
//            val grade = pre.getString("usergrade", "고등학교 2학년") // 기본값 설정

            val facilityList = arrayOf("중학교 1학년", "중학교 2학년", "중학교 3학년", "고등학교 1학년", "고등학교 2학년", "고등학교 3학년")

            val adapter = ArrayAdapter(context, R.layout.spinner_item, facilityList)

            val spinner = (context as AppCompatActivity).findViewById<Spinner>(spinnerId)
            spinner.adapter = adapter

            return spinner
        }
    }
}