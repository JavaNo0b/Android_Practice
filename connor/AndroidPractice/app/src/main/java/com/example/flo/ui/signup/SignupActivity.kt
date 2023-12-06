package com.example.flo.ui.signup

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.data.entities.User
import com.example.flo.data.remote.auth.AuthService
import com.example.flo.databinding.ActivitySignupBinding

class SignupActivity: AppCompatActivity(), SignUpView {
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupSignupTv.setOnClickListener{
            signUp()
        }
    }

    private fun getUser(): User {
        var email:String = binding.signupIdEt.text.toString() +"@"+ binding.signupEmailEt.text.toString()
        val pwd:String = binding.signupPassEt.text.toString()
        var name: String = binding.signupNameEt.text.toString()

        return User(email, pwd, name)
    }

//    private fun signUp(){
//        //회원가입 정보 입력 확인
//        if(binding.signupIdEt.text.toString().isEmpty() || binding.signupEmailEt.text.toString().isEmpty()){
//            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
//            return
//        }
//        if(binding.signupPassEt.text.toString().isEmpty() || binding.signupPassCheckEt.text.toString().isEmpty()){
//            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
//            return
//        }
//        if(binding.signupPassEt.text.toString() != binding.signupPassCheckEt.text.toString()){
//            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
//            return
//        }
//        //기입한 정보 User에 저장
//        val userDB = SongDatabase.getInstance(this)!!
//        userDB.userDao().insert(getUser())
//
//        val user = userDB.userDao().getUsers()
//        Log.d("SIGNUPACT", user.toString())
//    }

    private fun signUp(){
        //회원가입 정보 입력 확인
        if(binding.signupIdEt.text.toString().isEmpty() || binding.signupEmailEt.text.toString().isEmpty()){
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if(binding.signupNameEt.text.toString().isEmpty()){
            Toast.makeText(this, "이름 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        if(binding.signupPassEt.text.toString().isEmpty() || binding.signupPassCheckEt.text.toString().isEmpty()){
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if(binding.signupPassEt.text.toString() != binding.signupPassCheckEt.text.toString()){
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }



        val authService = AuthService()
        authService.setSignUpView(this)

        authService.signUp(getUser())
    }

    override fun onSignUpSuccess() {
        finish()
    }

    override fun onSignUpFailure(errorMsg: String) {
        binding.signupEmailErrorTv.visibility = View.VISIBLE
        binding.signupEmailErrorTv.text = errorMsg

        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
    }
}