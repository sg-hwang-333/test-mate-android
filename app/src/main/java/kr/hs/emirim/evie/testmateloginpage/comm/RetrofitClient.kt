package kr.hs.emirim.evie.testmateloginpage.comm

import android.content.Context
import kr.hs.emirim.evie.testmateloginpage.TMService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
class RetrofitClient {
    companion object {
        private const val BASE_URL = "http://10.0.2.2:8086"

        fun <T> create(service: Class<T>, context: Context? = null): T {
            val clientBuilder = OkHttpClient.Builder()

            // context가 null이 아닌 경우에만 세션 ID를 추가
            context?.let {
                val sessionId = SessionManager.getSessionId(it)
                sessionId?.let {
                    clientBuilder.addInterceptor { chain ->
                        val originalRequest = chain.request()
                        val requestWithCookie = originalRequest.newBuilder()
                            .addHeader("Cookie", "JSESSIONID=$it")
                            .build()
                        chain.proceed(requestWithCookie)
                    }
                }
            }

            val client = clientBuilder.build()

            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()

            return retrofit.create(service)
        }
    }
}