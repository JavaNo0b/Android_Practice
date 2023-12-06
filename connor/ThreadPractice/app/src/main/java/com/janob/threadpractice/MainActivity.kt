package com.janob.threadpractice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import com.janob.threadpractice.databinding.ActivityMainBinding
import java.util.logging.Handler

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val handler = android.os.Handler(Looper.getMainLooper()) //메인스레드의 고유 루퍼를 핸들러에 저장

        val imageList = arrayListOf<Int>() //img를 저장할 리스트

        imageList.add(R.drawable.img1)
        imageList.add(R.drawable.img2)
        imageList.add(R.drawable.img3)
        imageList.add(R.drawable.img1)
        imageList.add(R.drawable.img2)
        imageList.add(R.drawable.img3)
        imageList.add(R.drawable.img1)
        imageList.add(R.drawable.img2)
        imageList.add(R.drawable.img3)
        imageList.add(R.drawable.img1)
        imageList.add(R.drawable.img2)
        imageList.add(R.drawable.img3)
        imageList.add(R.drawable.img1)
        imageList.add(R.drawable.img2)
        imageList.add(R.drawable.img3)



        Thread{
            for(image in imageList){

                runOnUiThread{   //handler.post의 대체 함수 (같은 의미)
                    binding.iv.setImageResource(image)
                }
                Thread.sleep(1000)
            }
        }.start()



    //        val a = A()
    //        val b = B()
    //
    //        a.start()
    //        a.join()
    //        b.start()
    }

//    class A : Thread(){
//        override fun run() {
//            super.run()
//            for (i in 1..1000){
//                Log.d("test", "first : $i")
//            }
//        }
//    }
//    class B : Thread(){
//        override fun run() {
//            super.run()
//            for (i in 1000 downTo 1){
//                Log.d("test", "second : $i")
//            }
//        }
//    }
}