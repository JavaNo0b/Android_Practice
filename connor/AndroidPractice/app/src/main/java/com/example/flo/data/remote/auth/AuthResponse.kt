package com.example.flo.data.remote.auth

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName(value = "isSuccess") val isSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String,
    @SerializedName(value = "result") var result: Result? //로그인과 회원가입 api를 같은 데이터클래스로 받고 있으르모 ?표시를 해야 같이사용가능
    )

data class Result (
    @SerializedName(value = "userIdx") var userIdx:Int,
    @SerializedName(value = "jwt") var jwt:String
    )