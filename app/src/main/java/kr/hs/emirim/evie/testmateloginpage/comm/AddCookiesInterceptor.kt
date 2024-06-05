package kr.hs.emirim.evie.testmateloginpage.comm

import okhttp3.Interceptor
import okhttp3.Response

class AddCookiesInterceptor(private val sessionId: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // 세션 쿠키 추가
        val requestWithCookie = originalRequest.newBuilder()
            .addHeader("Cookie", "JSESSIONID=$sessionId")
            .build()

        return chain.proceed(requestWithCookie)
    }
}