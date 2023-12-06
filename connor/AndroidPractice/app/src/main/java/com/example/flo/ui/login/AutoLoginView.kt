package com.example.flo.ui.login

interface AutoLoginView {
    fun onAutoLoginSuccess(code : Int)
    fun onAutoLoginFailure()
}