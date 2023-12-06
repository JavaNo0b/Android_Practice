package com.example.flo.ui.song

import android.app.Activity
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.flo.R
import com.example.flo.data.local.SongDatabase
import com.example.flo.data.entities.Song
import com.example.flo.databinding.ActivitySongBinding
import com.example.flo.databinding.ToastCustomBinding
import com.example.flo.ui.main.MainActivity
import com.google.gson.Gson

class SongActivity : AppCompatActivity() { //:은 java의 extends와 같은 의미로 상속을 의미한다. 상속 받을 때는 ()를 넣어야 한다.// AppCompatActivity: 안드로이드에서 액티비티의 기능들을 사용할 수 있게 만들어 놓은 class
    lateinit var binding : ActivitySongBinding      //lateinit: 전방선언(선언은 먼저하고 초기화는 나중에 하겠다는 뜻) / var : 변경 가능한 변수 / val : 변경 불가능한 변수
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null // ?:null값도 저장할 수 있다는 의미, 액티비티가 종료될 때 미디어 플레이어도 종료해야 하므로
    private var gson : Gson = Gson() //gson은 코틀린을 json과 연결해주기 위한 도구이다.

    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDatabase
    var nowPos = 0

    // 변수 선언 방법 : var 변수명 : 변수타입 = 1
    override fun onCreate(savedInstanceState: Bundle?) { //액티비티가 생성될 때 가장 먼저 실행되는 함수
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater) //inflate : xml파일의 레이아웃들을 메모리에 객체화시키는 행동


        setContentView(binding.root) //xml에 있는 것들을 가져와서 마음껏 쓴다고 선언 //root : 최상위 레이아웃

        initPlayList()
        initSong() // Song 초기화
        initClickListener()


        val song = binding.songTitleTv.text.toString()
        binding.songDownIb.setOnClickListener(){
            val returnIntent = Intent(this, MainActivity::class.java).apply {
                putExtra(MainActivity.STRING_INTENT_KEY, song)
                Log.d("song return", song)
            }
            setResult(Activity.RESULT_OK, returnIntent)
            if(!isFinishing) finish()
        }
    }

    //[SongActivity] DB 데이터 songs에  Add
    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun initClickListener(){
        binding.songDownIb.setOnClickListener{  //down버튼을 누르면 액티비티 전환 종료
            finish()
        }

//        플레이 버튼
        binding.songMiniplayerIv.setOnClickListener{
            setPlayerStatus(false)
            serviceStart(this)
        }
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(true)
            serviceStop(this)
        }
//        반복 버튼
        binding.songUnrepeatIv.setOnClickListener{
            setRepeatStatus(false)
        }
        binding.songRepeatIv.setOnClickListener {
            setRepeatStatus(true)
        }
//        셔플 버튼
        binding.songUnrandomIv.setOnClickListener {
            setRandomStatus(false)
        }
        binding.songRandomIv.setOnClickListener {
            setRandomStatus(true)
        }
        binding.songMiniplayerNextIv.setOnClickListener {
            moveSong(+1)
        }
        binding.songMiniplayerPreviousIv.setOnClickListener {
            moveSong(-1)
        }
        binding.songLikeIv.setOnClickListener {
            setLike(songs[nowPos].isLike)
        }
    }


    private fun initSong(){   //Song에 대한 초기화
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        //[SongActivity] 저장 되어있는 'songId' (primary key)으로 songs와 비교하여 nowPos 값 찾기
        nowPos = getPlayingSongPosition(songId)
        Log.d("now Song ID",songs[nowPos].id.toString())

        startTimer() // song객체 초기화와 함께 실행
        setPlayer(songs[nowPos]) //뷰 랜더링
    }

    //[SongActivity] 하트 누르면 songs[nowPos], db 업데이트
    private fun setLike(isLike: Boolean){
        songs[nowPos].isLike = !isLike
        songDB.songDao().updateIsLikeById(!isLike,songs[nowPos].id)

        if (!isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
            toastText("좋아요 한 곡이 담겼습니다.")
            //토스트메세지
//            val context = this
//            CustomToast.createToast(context, "Welcome to blackjin Tistory")?.show()
        } else {
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
            toastText("좋아요 한 곡이 취소되었습니다.")
            ////토스트메세지
//            val context = this
//            CustomToast.createToast(context, "Welcome to blackjin Tistory")?.show()
        }
    }

    //[SongActivity] 다음곡 버튼 누르면 다음곡 이동,이전 곡 버튼 누르면 이전곡으로 이동 구현
    fun moveSong(direct: Int){
        if(nowPos + direct < 0){
            Toast.makeText(this, "first song", Toast.LENGTH_SHORT).show()
            return
        }
         if(nowPos + direct >= songs.size){
             Toast.makeText(this, "last song", Toast.LENGTH_SHORT).show()
             return
        }

        nowPos += direct
        //thread 재시작
        timer.interrupt()
        startTimer()

        mediaPlayer?.release() // 미디어플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null // 미디어 플레이어 해제

        setPlayerStatus(false)
        setPlayer(songs[nowPos])
    }

    //[SongActivity] 저장 되어있는 'songId' (primary key)으로 songs와 비교하여 nowPos 값 찾기
    fun getPlayingSongPosition(songId: Int): Int{
        for (i in 0 until songs.size){
            if (songs[i].id == songId){
                return i
            }
        }
        return 0
    }

    fun setPlayer(song: Song){ //song에 대한 값 뷰 렌더링
        binding.songTitleTv.text = song.title
        binding.songSingerTv.text = song.singer
        binding.songStarttimeTv.text = String.format("%02d:%02d",song.second / 60, song.second % 60)
        binding.songEndtimeTv.text = String.format("%02d:%02d",song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = (song.second * 100000 / song.playTime)
        binding.songAlbumIv.setImageResource(song.img)
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music) //mp3파일과 연결

        if (song.isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        } else {
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }

        setPlayerStatus(song.isPlaying)
    }

    fun toastText(text: String){
        val toastBinding = ToastCustomBinding.inflate(layoutInflater)

        toastBinding.run {
            toastTv.text = text

            val t2 = Toast(this@SongActivity)
            // View를 설정한다.
            t2.view = toastBinding.root

            // textView 글자 색상
            toastTv.setTextColor(Color.WHITE)

            // 위치 설정
            t2.setGravity(Gravity.CENTER or Gravity.BOTTOM,0,170.toPx())

            // 시간 설정
            t2.duration = Toast.LENGTH_LONG

            t2.show()
        }
    }

    private fun Int.toPx(): Int = (this * Resources.getSystem().displayMetrics.density).toInt()

    fun startTimer(){  //timer thread 를 시작하는 함수
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
        timer.start()
    }



    //inner class : 말 그래도 내부 클래스인데 class 안의 binding을 사용하기 위해 내부 클래스로 지정했다.
    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true): Thread(){

        private var second : Int = 0
        private var mills : Float = 0f

        override fun run() {
            super.run()
            try {
                while (true){

                    if(second >= playTime){ //second 와 playTime이 동일해지면 브레이크
                        runOnUiThread {
                            binding.songStarttimeTv.text = "00:00"
                        }
                        if(binding.songRepeatIv.visibility == View.VISIBLE){
                            mediaPlayer?.seekTo(0)
                            startTimer()
                            mediaPlayer?.start()
                            break
                        }else{
                            runOnUiThread {
                            setPlayerStatus(true)
                            }
                            mediaPlayer?.seekTo(0)
                            startTimer()
                            break
                        }


                    }

                    if(!isPlaying){
                        sleep(50)
                        mills += 50
                        runOnUiThread{
                            binding.songProgressSb.progress = ((mills/playTime)*100).toInt() // seekbar의 progress 위치
                        }

                        if(mills % 1000 == 0f){ //1초가 지났을 때 starttime 조정 훟 second 증가
                            runOnUiThread {
                                binding.songStarttimeTv.text = String.format("%02d:%02d",second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }
            }catch (e: InterruptedException){
                Log.d("Song","스레드가 죽었습니다. ${e.message}")
            }

        }


    }

    private fun setPlayerStatus(isPlaying : Boolean){
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying  //play버튼이 눌릴 때마다 각 클래스의 isPlaying값 초기화

        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            if(mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
            }
        }
        else{
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        }
    }
    fun setRepeatStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songUnrepeatIv.visibility = View.VISIBLE
            binding.songRepeatIv.visibility = View.GONE
        }
        else{
            binding.songUnrepeatIv.visibility = View.GONE
            binding.songRepeatIv.visibility = View.VISIBLE
        }
    }
    fun setRandomStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.songUnrandomIv.visibility = View.VISIBLE
            binding.songRandomIv.visibility = View.GONE
        }
        else{
            binding.songUnrandomIv.visibility = View.GONE
            binding.songRandomIv.visibility = View.VISIBLE
        }
    }


//    알림창띄우기
    fun serviceStart(view: SongActivity){
        val intent = Intent(this, Foreground::class.java) //서비스 호출할 인텐트
        ContextCompat.startForegroundService(this, intent)
    }
    fun serviceStop(view: SongActivity){
        val intent = Intent(this, Foreground::class.java) //서비스 호출할 인텐트
        stopService(intent)
    }


    //사용자가 포커스를 잃었을 때 노래 중지
    override fun onPause(){
        super.onPause()
        setPlayerStatus(true)
        songs[nowPos].second = ((binding.songProgressSb.progress * songs[nowPos].playTime)/100)/1000
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE) //내부 저장소에 저장할 수 있게 하는 클래스(에디터가 필요함)
        val editor = sharedPreferences.edit() // 에디터

        editor.putInt("songId", songs[nowPos].id)// song클래스의 내용들 모두 저장

        editor.apply() // 실제 저장
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() // 미디어플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null // 미디어 플레이어 해제
    }

}


