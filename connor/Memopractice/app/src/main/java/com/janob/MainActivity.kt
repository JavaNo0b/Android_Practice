package com.janob.memopractice

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.janob.memopractice.R
import com.janob.memopractice.databinding.ActivityMainBinding
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.util.Utility
import com.kakao.sdk.user.UserApiClient

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /** HashKey확인 */
        val keyHash = Utility.getKeyHash(this)
        TextMsg(this, "HashKey: ${keyHash}")

        /** KakoSDK init */
        KakaoSdk.init(this, this.getString(R.string.kakao_app_key))

        /** Click_listener */
        binding.login.setOnClickListener {
            kakaoLogin() //로그인
        }
        binding.logout.setOnClickListener {
            kakaoLogout() //로그아웃
        }
//        binding.btnStartKakaoUnlink.setOnClickListener {
//            kakaoUnlink() //연결해제
//        }
        binding.profile.setOnClickListener {
            val intent = Intent(this, MemoActivity::class.java)
            startActivity(intent)
        }
    }

    private fun kakaoLogin() {
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                TextMsg(this, "카카오계정으로 로그인 실패 : ${error}")
                setLogin(false)
            } else if (token != null) {
                //TODO: 최종적으로 카카오로그인 및 유저정보 가져온 결과
                UserApiClient.instance.me { user, error ->
                    TextMsg(this, "카카오계정으로 로그인 성공 \n\n " +
                            "token: ${token.accessToken} \n\n " +
                            "me: ${user}")
                    setLogin(true)
                }
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(this)) {
            UserApiClient.instance.loginWithKakaoTalk(this) { token, error ->
                if (error != null) {
                    TextMsg(this, "카카오톡으로 로그인 실패 : ${error}")

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    TextMsg(this, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    setLogin(true)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
        }
    }

    private fun kakaoLogout(){
        // 로그아웃
        UserApiClient.instance.logout { error ->
            if (error != null) {
                TextMsg(this, "로그아웃 실패. SDK에서 토큰 삭제됨: ${error}")
            }
            else {
                TextMsg(this, "로그아웃 성공. SDK에서 토큰 삭제됨")
                setLogin(false)
            }
        }
    }

//    private fun kakaoUnlink(){
//        // 연결 끊기
//        UserApiClient.instance.unlink { error ->
//            if (error != null) {
//                TextMsg(this, "연결 끊기 실패: ${error}")
//            }
//            else {
//                TextMsg(this, "연결 끊기 성공. SDK에서 토큰 삭제 됨")
//                setLogin(false)
//            }
//        }
//    }

    private fun TextMsg(act: Activity, msg : String){
        binding.hashkey.text = msg
    }

    private fun setLogin(bool: Boolean){
        binding.login.visibility = if(bool) View.GONE else View.VISIBLE
        binding.logout.visibility = if(bool) View.VISIBLE else View.GONE
//        binding.btnStartKakaoUnlink.visibility = if(bool) View.VISIBLE else View.GONE
    }
}
import java.util.ArrayList;
import java.util.Vector;
public class VectorToArrayListManipulationExample {
    public static void main(String[] args) {
// Vector 생성 및 데이터 추가
        Vector<String> vector = new Vector<>();
        vector.add("Apple");
        vector.add("Banana");
        vector.add("Orange");
        System.out.println("Vector의 데이터:");
        for (String value : vector)
        System.out.println(value);
// ArrayList 생성
        ArrayList<String> arrayList = new ArrayList<>();
// Vector에서 데이터를 제거하면서 ArrayList로 이동
        while (!vector.isEmpty()) {
            String removedElement = vector.remove(0); // Vector의 첫 번째 요소 제거
            arrayList.add(removedElement); // 제거한 요소를 ArrayList에 추가
        }
// Vector의 데이터 출력 (이제 비어있어야 함)
        if(vector.isEmpty()){
            System.out.println("Vector의 데이터는 비었음");
        }
// ArrayList의 데이터 출력
        System.out.println("\nArrayList의 데이터:");
        for (String value : arrayList)
        System.out.println(value);
    }
}