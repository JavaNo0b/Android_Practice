package com.example.flo.ui.main.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.flo.ui.main.home.HomeFragment
import com.example.flo.R
import com.example.flo.data.local.SongDatabase
import com.example.flo.data.entities.Album
import com.example.flo.data.entities.Like
import com.example.flo.databinding.FragmentAlbumBinding
import com.example.flo.ui.main.MainActivity
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding

    private val information = arrayListOf("수록곡", "상세정보", "영상")  //탭의 이름들을 담기 위한 배열

    private var isLiked : Boolean = false

    lateinit var albumDB: SongDatabase
    lateinit var album: Album
    var nowPos = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        initAlbum()
        isLiked = isLikedAlbum(album.id)

        setInit()
        setOnClickListeners(album)

        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(
                R.id.main_frm,
                HomeFragment()
            ).commitAllowingStateLoss()
        }
        val albumId = arguments?.getInt("albumIdx")
        val albumAdapter = AlbumVPAdapter(this, albumId!!)
        binding.albumContentVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp){ //첫번째 인자로는 TAB, 두번째 인자로는 ViewPager
            tab, position ->
            tab.text = information[position]
        }.attach() //tab과 viewpager를 붙이기 위한 함수

        return binding.root
    }

    private fun initAlbum(){
        albumDB = SongDatabase.getInstance(requireContext())!!
        var todayAlbumId = arguments?.getInt("albumId")//키가 앨범인 데이터 arguments에서 꺼내기
        nowPos = todayAlbumId!!
        album = albumDB.albumDao().getAlbum(nowPos)
    }

    private fun setTodayInit() {
        if(album.coverImg == null || album.coverImg == 0){

        }else{
            binding.albumImgIv.setImageResource(album.coverImg!!)
        }
        binding.albumTitleTv.text = album.title
        binding.albumSingerTv.text = album.singer
        if(isLiked){
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun setInit() {
        val albumTitle = arguments?.getString("albumTitle")
        val albumSinger = arguments?.getString("albumSinger")
        val albumImg = arguments?.getString("albumImg")

        context?.let { Glide.with(it).load(albumImg).into(binding.albumImgIv) }
        binding.albumTitleTv.text = albumTitle
        binding.albumSingerTv.text = albumSinger
        if(isLiked){
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun getJwt():Int{
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt",0) //가져온 값이 없다면 0을 반환
    }

    //좋아요를 눌렀을 때 like테이블에 추가
    private fun likeAlbum(userId : Int, albumId : Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId,albumId)

        songDB.albumDao().likeAlbum(like)
    }
    //좋아요를 눌렀는지 확인
    private fun isLikedAlbum(albumId: Int): Boolean{
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        val likeId : Int? = songDB.albumDao().isLikedAlbum(userId, albumId)

        return likeId != null //눌렸으면 true 아니면 false
    }
    private fun disLikedAlbum(albumId: Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        songDB.albumDao().disLikedAlbum(userId, albumId)
    }

    private fun setOnClickListeners(album: Album){
        val userId = getJwt()
        binding.albumLikeIv.setOnClickListener {
            if(isLiked){
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                disLikedAlbum(album.id)
            }else{
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId, album.id)
            }
            isLiked = false
        }
    }
}