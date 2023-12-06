package com.example.flo.data.remote.auth

import com.example.flo.data.entities.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/users")
    fun signUp(@Body user: User): Call<AuthResponse>

    @POST("/users/login")
    fun login(@Body user: User): Call<AuthResponse>

    @GET("/users/auto-login")
    fun autoLogin(@Header("X-ACCESS-TOKEN") accessToken: String?): Call<AuthResponse>
}