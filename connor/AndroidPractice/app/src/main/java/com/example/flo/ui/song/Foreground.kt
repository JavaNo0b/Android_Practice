package com.example.flo.ui.song

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.flo.R
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread

class Foreground : Service() {

    private var notificationJob: Job? = null
    private val notificationDispatches: CoroutineDispatcher = Dispatchers.Default

    val CHANNEL_ID = "FG8454"
    val NOTI_ID = 99


    fun createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val serviceChannel = NotificationChannel(CHANNEL_ID, "FOREGROUND", NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(serviceChannel) // 이제 이 채널로 사용하겠다고 시스템에게 알려줌
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        createNotificationChannel()
        notificationJob = CoroutineScope(notificationDispatches).launch {
            for(i in 1..100){
                updateNotificationProgress(i)
                delay(1000)
            }
        }

        runBackGround()
        return super.onStartCommand(intent, flags, startId)
    }

    fun runBackGround(){
        thread(start=true){
            for (i in 0..100){
                Thread.sleep(1000)
                Log.d("service", "COUNT===>$i")
            }
        }
    }

    fun updateNotificationProgress(progress: Int){
        val intent = Intent(this, SongActivity::class.java)
        val pendingIntent: PendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_MUTABLE)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Flo") //notification에 보여주는 타이틀
            .setContentText("노래 재생중")
            .setSmallIcon(R.drawable.ic_bottom_home_no_select) // notification에 보여지는 아이콘
            .setContentIntent(pendingIntent)
            .setProgress(100, progress,false)
            .build()

        startForeground(NOTI_ID, notification) // 내가 사용하는 서비스가 포어그라운드로 진행된다는 걸 알려주기
    }

    override fun onBind(intent: Intent): IBinder {
        return Binder()
    }
}