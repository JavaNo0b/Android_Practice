package com.example.flo.ui.main

import android.app.Activity
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.flo.ui.main.home.HomeFragment
import com.example.flo.ui.main.locker.LockerFragment
import com.example.flo.ui.main.look.LookFragment
import com.example.flo.R
import com.example.flo.ui.main.search.SearchFragment
import com.example.flo.ui.song.SongActivity
import com.example.flo.data.local.SongDatabase
import com.example.flo.data.entities.Album
import com.example.flo.data.entities.Song
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private var gson : Gson = Gson()
    private var mediaPlayer: MediaPlayer? = null
    lateinit var timer: Timer
    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDatabase
    var nowPos = 0

    companion object { const val STRING_INTENT_KEY = "song_title"}
    private val getResultText = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            val returnString = result.data?.getStringExtra(STRING_INTENT_KEY).toString()
            Toast.makeText(this@MainActivity, returnString, Toast.LENGTH_SHORT).show()
            Log.d("song_title", returnString)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setTheme(R.style.Theme_FLO) //메인 액티비티가 실행될 때 다시 flo테마로 돌아오게 함

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummySongs()
        inputDummyAlbums()
        initPlayList()
        initSong()
        initClickListener()


        initBottomNavigation()
        Log.d("Song",songs[nowPos].title+songs[nowPos].singer)

        Log.d("MAIN/JWT_TO_SERVER",getJwt().toString())
    }

    //[SongActivity] DB 데이터 songs에  Add
    private fun initPlayList(){
        songDB = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun initSong(){   //Song에 대한 초기화
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        //[SongActivity] 저장 되어있는 'songId' (primary key)으로 songs와 비교하여 nowPos 값 찾기
        nowPos = SongActivity().getPlayingSongPosition(songId)
        Log.d("now Song ID",songs[nowPos].id.toString())

        songs[nowPos] = if(songId == 0){
            songDB.songDao().getSong(1)
        }else {
            songDB.songDao().getSong(songId)
        }

        Log.d("song ID", songs[nowPos].id.toString())

        startTimer() // song객체 초기화와 함께 실행
        setMiniPlayer(songs[nowPos]) //뷰 랜더링
    }

    private fun initClickListener(){
        binding.mainPlayerCl.setOnClickListener {
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", songs[nowPos].id)
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)
        }
//      미니플레이어 플레이 버튼 변환
        binding.mainMiniplayerBtn.setOnClickListener{
            setPlayerStatus(false)
        }
        binding.mainPauseBtn.setOnClickListener{
            setPlayerStatus(true)
        }
        //다음곡,이전곡 재생
        binding.mainMiniplayerNextBtn.setOnClickListener {
            moveSong(+1)
        }
        binding.mainMiniplayerPreviousBtn.setOnClickListener {
            moveSong(-1)
        }
    }

    //[MainAvtivity] 다음곡 버튼 누르면 다음곡 이동,이전 곡 버튼 누르면 이전곡으로 이동 구현
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
        setMiniPlayer(songs[nowPos])
    }

    fun setMiniPlayer(song: Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.mainProgressSb.progress = (song.second * 100000)/song.playTime
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music) //mp3파일과 연결

        setPlayerStatus(song.isPlaying)
    }

    fun setPlayerStatus(isPlaying : Boolean){
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying  //play버튼이 눌릴 때마다 각 클래스의 isPlaying값 초기화

        if(isPlaying){
            binding.mainMiniplayerBtn.visibility = View.VISIBLE
            binding.mainPauseBtn.visibility = View.GONE
            if(mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
            }
        }
        else{
            binding.mainMiniplayerBtn.visibility = View.GONE
            binding.mainPauseBtn.visibility = View.VISIBLE
            mediaPlayer?.start()
        }
    }

    private fun getJwt():String?{
        val spf = this.getSharedPreferences("auth2",AppCompatActivity.MODE_PRIVATE)
        return spf!!.getString("jwt","") //가져온 값이 없다면 0을 반환
    }

    override fun onStart() {
        super.onStart()

    }

    //더미 데이터 데이터베이스에 삽입하는 함수
    //[MainActivity] DB에 더미데이터 insert
    private fun inputDummySongs(){
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        if(songs.isNotEmpty()) return

        songDB.songDao().insert(
            Song(
                "Butter",
                "방탄소년단 (BTS)",
                0,
                30,
                true,
                "music_butter",
                R.drawable.img_album_exp,
                false,
                1,
            )
        )
        songDB.songDao().insert(
            Song(
                "LILAC",
                "아이유 (IU)",
                0,
                30,
                true,
                "music_lilac",
                R.drawable.img_album_exp2,
                false,
                2,
            )
        )
        songDB.songDao().insert(
            Song(
                "우리도 지나가지만",
                "정훈",
                0,
                30,
                true,
                "music_jung",
                R.drawable.ic_browse_arrow_right,
                false,
                2,
            )
        )
        songDB.songDao().insert(
            Song(
                "Next Level",
                "에스파 (AESPA)",
                0,
                30,
                true,
                "music_next",
                R.drawable.img_album_exp3,
                false,
                3,
            )
        )
        songDB.songDao().insert(
            Song(
                "Boy with Luv",
                "방탄소년단 (BTS)",
                0,
                30,
                true,
                "music_boy",
                R.drawable.img_album_exp4,
                false,
                4,
            )
        )
        songDB.songDao().insert(
            Song(
                "BBoom BBoom",
                "모모랜드 (MOMOLAND)",
                0,
                30,
                true,
                "music_bboom",
                R.drawable.img_album_exp5,
                false,
                5,
            )
        )
        songDB.songDao().insert(
            Song(
                "Weekend",
                "태연 (Tae Yeon)",
                0,
                30,
                true,
                "music_jung",
                R.drawable.img_album_exp6,
                false,
                6,
            )
        )

        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
    }

    private fun inputDummyAlbums(){
        val songDB = SongDatabase.getInstance(this)!!
        val albums = songDB.albumDao().getAlbums()

        if(albums.isNotEmpty()) return

        songDB.albumDao().insert(
            Album(
                0,
                "Butter",
                "방탄소년단 (BTS)",
                R.drawable.img_album_exp,
            )
        )
        songDB.albumDao().insert(
            Album(
                1,
                "LILAC",
                "아이유 (IU)",
                R.drawable.img_album_exp2,
            )
        )
        songDB.albumDao().insert(
            Album(
                2,
                "iScreaM Vol.10 : Next Level Remixes",
                "에스파 (AESPA)",
                R.drawable.img_album_exp3,
            )
        )
        songDB.albumDao().insert(
            Album(
                3,
                "MAP OF THE SOUL : PERSONA",
                "방탄소년단 (BTS)",
                R.drawable.img_album_exp4,
            )
        )
        songDB.albumDao().insert(
            Album(
                4,
                "GREAT!",
                "모모랜드 (MOMOLAND)",
                R.drawable.img_album_exp5,
            )
        )
        songDB.albumDao().insert(
            Album(
                5,
                "Weekend",
                "태연 (Tae Yeon)",
                R.drawable.img_album_exp6,
            )
        )
        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
    }

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
                            setPlayerStatus(true)
                        }
                        mediaPlayer?.seekTo(0)
                        startTimer()
                        break


                    }

                    if(!isPlaying){
                        sleep(50)
                        mills += 50
                        runOnUiThread{
                            binding.mainProgressSb.progress = ((mills/playTime)*100).toInt() // seekbar의 progress 위치
                        }

                        if(mills % 1000 == 0f){ //1초가 지났을 때 second 증가
                            second++
                        }
                    }
                }
            }catch (e: InterruptedException){
                Log.d("Song","스레드가 죽었습니다. ${e.message}")
            }

        }


    }
    //사용자가 포커스를 잃었을 때 노래 중지
    override fun onPause(){
        super.onPause()
        setPlayerStatus(true)
        songs[nowPos].second = ((binding.mainProgressSb.progress * songs[nowPos].playTime)/100)
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE) //내부 저장소에 저장할 수 있게 하는 클래스(에디터가 필요함)
        val editor = sharedPreferences.edit() // 에디터

        editor.putInt("songId", songs[nowPos].id)// song클래스의 내용들 모두 저장

        editor.apply() // 실제 저장
    }


    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }

}