package kr.hs.emirim.evie.testmateloginpage.comm

import kr.hs.emirim.evie.testmateloginpage.TMService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class RetrofitClient {
    companion object {
        private const val BASE_URL = "http://192.168.1.67:8080"

        fun <T> create(service: Class<T>): T {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(service)
        }
    }
}