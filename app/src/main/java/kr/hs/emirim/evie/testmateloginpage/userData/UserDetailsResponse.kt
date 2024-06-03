package kr.hs.emirim.evie.testmateloginpage.userData

import com.google.gson.annotations.SerializedName

data class UserDetailsResponse(
    @SerializedName("name") val name: String,
    @SerializedName("grade") val grade: Int
)