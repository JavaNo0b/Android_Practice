package com.janob.signup

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.janob.signup.databinding.ActivitySignupBinding

class SignupActivity: AppCompatActivity() {

    lateinit var binding: ActivitySignupBinding
//    lateinit var signUpViewModel: SignUpViewModel
    val signUpViewModel: SignUpViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_signup)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
//        signUpViewModel = ViewModelProvider(this)[SignUpViewModel::class.java]
        binding.viewModel = signUpViewModel
        binding.lifecycleOwner = this

        setObserve()
        signUpViewModel.validate()
    }

    private fun setObserve(){
        // 비밀번호, 비밀번호 재확인에 값이 들어있을때만 오류나 일치가 뜨게
        signUpViewModel.passwordCheck.observe(this){
            if(!signUpViewModel.passwordCheck()) {
                binding.tlPasswordCheck.error = "비밀번호가 일치하지 않습니다."
                signUpViewModel.passwordCheckVisible.value = false
            } else {
                binding.tlPasswordCheck.error = null
                signUpViewModel.passwordCheckVisible.value = true
            }
        }

        signUpViewModel.signUpBtnOn.observe(this){
            if(it){
                binding.btnSignup.setBackgroundResource(R.drawable.btn_all_round_blue)
                binding.btnSignup.isClickable = true
            } else{
                binding.btnSignup.setBackgroundResource(R.drawable.btn_all_round_black)
                binding.btnSignup.isClickable = false
            }
        }


    }
}