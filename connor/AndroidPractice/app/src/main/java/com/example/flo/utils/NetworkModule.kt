package com.example.flo.utils

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//공통적으로 쓰이는 함수
const val BASE_URL = "https://edu-api-test.softsquared.com" // 슬래시를 붙이면 안된다.

//retrofit 객체 만들기
fun getRetrofit(): Retrofit {
    val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    return retrofit
}