package kr.hs.emirim.evie.testmateloginpage.comm

import kr.hs.emirim.evie.testmateloginpage.TMService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class RetrofitClient {
    companion object {
        private const val BASE_URL = "http://172.22.64.1:8086"

        fun create(): TMService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(TMService::class.java)
        }
    }
}