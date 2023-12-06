package com.example.flo.ui.signup

interface SignUpView { //activity와 authService를 연결해주는 곳
    fun onSignUpSuccess()
    fun onSignUpFailure(msg: String)
}