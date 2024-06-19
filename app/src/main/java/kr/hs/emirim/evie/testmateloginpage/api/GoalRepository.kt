package kr.hs.emirim.evie.testmateloginpage.api

import androidx.appcompat.app.AppCompatActivity
import kr.hs.emirim.evie.testmateloginpage.comm.RetrofitClient
import kr.hs.emirim.evie.testmateloginpage.userData.UserDetailsResponse

class GoalRepository : AppCompatActivity() {
    companion object {
        val goalAPIService = RetrofitClient.create(GoalAPIService::class.java)



    }
}