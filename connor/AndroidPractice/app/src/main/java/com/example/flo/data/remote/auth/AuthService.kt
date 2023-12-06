package com.example.flo.data.remote.auth

import android.util.Log
import com.example.flo.ui.login.AutoLoginView
import com.example.flo.ui.login.LoginView
import com.example.flo.ui.signup.SignUpView
import com.example.flo.data.entities.User
import com.example.flo.utils.getRetrofit
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView
    private lateinit var loginView: LoginView
    private lateinit var autoLoginView: AutoLoginView

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }
    fun setLoginView(loginView: LoginView){
        this.loginView = loginView
    }
    fun setAutoLoginView(autoLoginView: AutoLoginView){
        this.autoLoginView = autoLoginView
    }

    fun signUp(user: User){ //회원가입 api를 확인하고 관리
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.signUp(user).enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("SIGNUP/SUCCESS", response.toString())
                val resp: AuthResponse = response.body()!!
                when(resp.code){
                    1000 -> signUpView.onSignUpSuccess()
                    else -> signUpView.onSignUpFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }

        }) //user의 정보로 API호출
        Log.d("SIGNUP", "HELLO")
    }
    fun login(user: User){ //회원가입 api를 확인하고 관리
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.login(user).enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("Login/SUCCESS", response.toString())
                val resp: AuthResponse = response.body()!!
                when(val code = resp.code){
                    1000 -> loginView.onLoginSuccess(code, resp.result!!)
                    else -> loginView.onLoginFailure(resp.message)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("Login/FAILURE", t.message.toString())
            }

        }) //user의 정보로 API호출
        Log.d("Login", "HELLO")
    }

    fun autoLogin(accessToken: String?){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)

        authService.autoLogin(accessToken).enqueue(object: Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("AutoLogin/SUCCESS", response.toString())
                val resp: AuthResponse = response.body()!!

                when(val code = resp.code){
                    1000 -> autoLoginView.onAutoLoginSuccess(code)
                    else -> autoLoginView.onAutoLoginFailure()
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("AutoLogin/FAILURE", t.message.toString())
            }

        })
    }
}