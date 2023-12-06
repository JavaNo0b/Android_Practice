package com.janob.apipractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.janob.apipractice.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding

    private val dustViewModel by viewModels<DustViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dustViewModel.communicateNetWork(setUpDustParameter())
        observeDustData()
    }
    private fun observeDustData(){
        dustViewModel.dustData.observe(this){
            it?.let {
                Log.d("Dust", it.toString())
                binding.text.text = it.toString()
            }
        }
    }
    private fun setUpDustParameter(): HashMap<String, String>{

        return hashMapOf(
            "serviceKey" to "1iXwT9QMFQzWgLDP7QEehFJgKBu37+k1P0ayxdPCQVyE/Djv1ja3raTchYiIITKMRtNmsr15MT0e1xg2y4WxfA==", // OPEN API 의 인증키 중 Decoding된 것을 사용
            "returnType" to "json",
            "numOfRows" to "100",
            "pageNo" to "1",
            "sidoName" to "서울",
            "ver" to "1.0"
        )

    }
}