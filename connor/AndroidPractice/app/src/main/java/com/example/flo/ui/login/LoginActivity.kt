package com.example.flo.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.ui.signup.SignupActivity
import com.example.flo.data.entities.User
import com.example.flo.data.remote.auth.AuthService
import com.example.flo.data.remote.auth.Result
import com.example.flo.databinding.ActivityLoginBinding
import com.example.flo.ui.main.MainActivity

class LoginActivity: AppCompatActivity(), LoginView {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignupTv.setOnClickListener{
            startActivity(Intent(this, SignupActivity::class.java))
        }

        binding.loginBtnTv.setOnClickListener{
            login()
        }
    }
    private fun login(){
        if(binding.loginIdEt.text.toString().isEmpty() || binding.loginEmailEt.text.toString().isEmpty()){
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        if(binding.loginPassEt.text.toString().isEmpty()){
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        var email:String = binding.loginIdEt.text.toString() +"@"+ binding.loginEmailEt.text.toString()
        val pwd:String = binding.loginPassEt.text.toString()

//        val userDB = SongDatabase.getInstance(this)!!
//        val user = userDB.userDao().getUser(email, pwd) //입력한 이메일과 비밀번호에 일치하는 user데이터 받기
//
//        if(user != null){
//            Log.d("LOGIN_ACT/GET_USER", "userId : ${user.id}, $user")
////            saveJwt(user.id)
//            Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
//            startMainActivity()
//        }else{
//            Toast.makeText(this, "회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
//        }
        val authService = AuthService()
        authService.setLoginView(this)

        authService.login(User(email, pwd, ""))
    }

    //인자 값으로 받은 jwt를 getSharedPreferences로 저장
//    private fun saveJwt(jwt:Int){
//        val spf = getSharedPreferences("auth", MODE_PRIVATE)
//        val editor = spf.edit()
//
//        editor.putInt("jwt",jwt)
//        editor.apply()
//    }

    private fun saveJwt2(jwt:String){
        val spf = getSharedPreferences("auth2", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putString("jwt",jwt)
        editor.apply()
    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onLoginSuccess(code : Int, result: Result) {
        when(code){
            1000 -> {
                saveJwt2(result.jwt)
                Log.d("LOGIN", result.jwt)
                startMainActivity()
            }
        }
    }

    override fun onLoginFailure(errorMsg: String) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_SHORT).show()
    }
}