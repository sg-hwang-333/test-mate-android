package kr.hs.emirim.evie.testmateloginpage.api

import kr.hs.emirim.evie.testmateloginpage.userData.UserDetailsResponse
import retrofit2.Call
import retrofit2.http.GET

interface UserAPIService {
    @GET("api/user/details")
    fun getUserDetails() : Call<UserDetailsResponse>

}