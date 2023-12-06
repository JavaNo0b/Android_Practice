package com.janob.memopractice

import com.janob.memopractice.CheckActivity

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.janob.memopractice.databinding.ActivityMemoBinding

//EditText의 data 저장 전역 변수
var textData: String = ""

class MemoActivity : AppCompatActivity() {

    lateinit var binding: ActivityMemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            val intent = Intent(this, CheckActivity::class.java)
            intent.putExtra("text", binding.edittext.text.toString())
            startActivity(intent)
        }

        Log.d("MemoActivity", "onCreate()")
    }

    override fun onStart() {
        super.onStart()
        Log.d("MemoActivity", "onStart()")
    }

    override fun onPause() {
        super.onPause()
        textData = binding.edittext.text.toString()

        Log.d("MemoActivity", "onPause()")
    }

    override fun onResume() {
        super.onResume()
        if (textData != ""){
            binding.edittext.setText(textData)
        }

        Log.d("MemoActivity", "onResume()")
    }

    override fun onStop() {
        super.onStop()
        Log.d("MemoActivity", "onStop()")
    }

    override fun onRestart() {
        super.onRestart()

        AlertDialog.Builder(this)
            .setTitle("Memo")
            .setMessage("이어서 작성하시겠습니까?!?!")
            .setNegativeButton("아니요") { dialog, which ->
                // Respond to negative button press
                textData = ""
                binding.edittext.setText(textData)
            }
            .setPositiveButton("예") { dialog, which ->
                // Respond to positive button press
                binding.edittext.setText(textData)
            }
            .create()
            .show()

        Log.d("MemoActivity", "onRestart()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("MemoActivity", "onDestroy()")
    }

}