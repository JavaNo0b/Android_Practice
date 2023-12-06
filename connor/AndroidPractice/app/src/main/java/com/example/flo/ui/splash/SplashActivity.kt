package com.example.flo.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.ui.login.AutoLoginView
import com.example.flo.data.remote.auth.AuthService
import com.example.flo.databinding.ActivitySplashBinding
import com.example.flo.ui.login.LoginActivity
import com.example.flo.ui.main.MainActivity

class SplashActivity: AppCompatActivity(), AutoLoginView {

    lateinit var  binding: ActivitySplashBinding
    val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val authService = AuthService()
        authService.setAutoLoginView(this)
        val token = getJwt2()
        authService.autoLogin(token)
    }

    private fun getJwt2(): String?{
        val spf = getSharedPreferences("auth2", MODE_PRIVATE)
        return spf.getString("jwt", null)
    }

    override fun onAutoLoginSuccess(code: Int) {
        handler.postDelayed({
            when(code){
                1000 -> {
                    startActivity(Intent(this, MainActivity::class.java))
                }
            }
        },1000)
    }

    override fun onAutoLoginFailure() {
        handler.postDelayed({
            startActivity(Intent(this, LoginActivity::class.java))
        },1000)
    }
}