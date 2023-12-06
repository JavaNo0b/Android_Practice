package com.example.udemy_android_template.config

import com.example.udemy_android_template.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.example.udemy_android_template.utils.getJwt
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class XAccessTokenInterceptor: Interceptor { //Interceptor: api를 요청할 때마다 자동적으로 sharedPreference에 저장되어있는 jwt를 가져와서 헤더에 넣어줌 / 헤더에 jwt를 넣는 작업을 따로 해 줄 필요없음
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()

        val jwtToken: String? = getJwt()

        jwtToken?.let{
            builder.addHeader(X_ACCESS_TOKEN, jwtToken)
        }

        return chain.proceed(builder.build())
    }
}