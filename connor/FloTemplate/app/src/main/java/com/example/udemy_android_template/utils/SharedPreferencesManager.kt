package com.example.udemy_android_template.utils

import com.example.udemy_android_template.ApplicationClass.Companion.X_ACCESS_TOKEN
import com.example.udemy_android_template.ApplicationClass.Companion.mSharedPreferences


fun saveJwt(jwtToken: String) {
    val editor = mSharedPreferences.edit()
    editor.putString(X_ACCESS_TOKEN, jwtToken)

    editor.apply()
}

fun getJwt(): String? = mSharedPreferences.getString(X_ACCESS_TOKEN, null)
