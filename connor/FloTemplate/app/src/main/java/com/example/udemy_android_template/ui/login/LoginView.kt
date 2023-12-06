package com.example.udemy_android_template.ui.login

import com.example.udemy_android_template.data.remote.auth.Auth


interface LoginView {
    fun onLoginLoading()
    fun onLoginSuccess(auth: Auth)
    fun onLoginFailure(code: Int, message: String)
}