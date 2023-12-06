package com.janob.memopractice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.janob.memopractice.databinding.ActivityCheckBinding

class CheckActivity: AppCompatActivity() {
    lateinit var binding: ActivityCheckBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCheckBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(intent.hasExtra("text")){
            binding.checkText.text = intent.getStringExtra("text")
        }
        Log.d("CheckActivity", "onCreate()")
    }

    override fun onStart() {
        super.onStart()
        Log.d("CheckActivity", "onStart()")
    }

    override fun onResume() {
        super.onResume()
        Log.d("CheckActivity", "onResume()")
    }

    override fun onPause() {
        super.onPause()
        Log.d("CheckActivity", "onPause()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("CheckActivity", "onStop()")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("CheckActivity", "onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("CheckActivity", "onDestroy()")
    }
}