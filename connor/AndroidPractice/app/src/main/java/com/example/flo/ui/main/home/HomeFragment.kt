package com.example.flo.ui.main.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.ui.main.album.AlbumView
import com.example.flo.ui.main.home.banner.BannerFragment
import com.example.flo.ui.main.home.banner.BannerVPAdapter
import com.example.flo.ui.main.home.pannel.PannelVPAdapter
import com.example.flo.R
import com.example.flo.data.local.SongDatabase
import com.example.flo.data.entities.Album
import com.example.flo.data.entities.Song
import com.example.flo.data.remote.album.AlbumService
import com.example.flo.data.remote.album.AlbumSongs
import com.example.flo.data.remote.album.AlbumSongsResult
import com.example.flo.databinding.FragmentHomeBinding
import com.example.flo.ui.main.MainActivity
import com.example.flo.ui.main.album.AlbumFragment
import me.relex.circleindicator.CircleIndicator3

class HomeFragment : Fragment(), AlbumView {

    lateinit var binding: FragmentHomeBinding
    lateinit var albumSongsAdapter: AlbumPotRVAdapter

    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDatabase
    private var albumDatas = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        songDB = SongDatabase.getInstance(requireContext())!!
        albumDatas.addAll(songDB.albumDao().getAlbums())

        initSongList()

        val albumRVAdapter = AlbumRVAdapter(albumDatas) //어댑터와 데이터 연결
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter //RecyclerView의 어댑터 설정
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        //레이아웃 매니저를 horizontal linearLayout으로 설정

        albumRVAdapter.setMyItemClickLitner(object: AlbumRVAdapter.MyItemClickListner {

            override fun onPlayClick(album: Album) {
                val mActivity = activity as MainActivity
                val songs = songDB.songDao().getSongsInAlbum(album.id)
                mActivity.setMiniPlayer(songs.first())
                Log.d("onAlbum","albumPlay")
            }

            override fun onItemClick(album: Album) {
                changeTodayAlbumFragment(album)
            }
        })

        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeViewpagerMusicVp.adapter = bannerAdapter
        binding.homeViewpagerMusicVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val pannelAdapter = PannelVPAdapter(this)
        binding.homePannelContentVp.adapter = pannelAdapter
        binding.homePannelContentVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val indicator : CircleIndicator3 = binding.homeIndicator
        indicator.setViewPager(binding.homePannelContentVp)


        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getAlbums()
    }

    private fun getAlbums() {
        val albumService = AlbumService()
        albumService.setAlbumView(this)

        albumService.getAlbums()
    }

    private fun initSongList(){
        songDB = SongDatabase.getInstance(requireContext())!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun initRecyclerView(result: AlbumSongsResult){
        albumSongsAdapter = AlbumPotRVAdapter(requireContext(), result)

        binding.homePotcastAlbumRv.adapter = albumSongsAdapter

        albumSongsAdapter.setMyItemClickListener(object: AlbumPotRVAdapter.MyItemClickListener {
            override fun onItemClick(album: AlbumSongs) {
                changeAlbumFragment(album)
                Log.d("onAlbum", "albumClick")
            }
        })
    }
    private fun changeTodayAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    putInt(
                        "albumId",
                        album.id
                    )
                }
            }).commitAllowingStateLoss()
    }
    private fun changeAlbumFragment(album: AlbumSongs) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    putInt(
                        "albumIdx",
                        album.albumIdx
                    )
                    putString(
                        "albumSinger",
                        album.singer
                    )
                    putString(
                        "albumImg",
                        album.coverImgUrl
                    )
                    putString(
                        "albumTitle",
                        album.title
                    )
                }
            }).commitAllowingStateLoss()
    }


    override fun onGetAlbumLoading() {
        binding.homePotcastLoadingPb.visibility = View.VISIBLE
        Log.d("POT", "load")
    }

    override fun onGetAlbumSuccess(code: Int, result: AlbumSongsResult) {
        binding.homePotcastLoadingPb.visibility = View.GONE
        initRecyclerView(result)
        Log.d("POT", "success")
    }

    override fun onGetAlbumFailure(message: String) {
        binding.homePotcastLoadingPb.visibility = View.GONE
        Log.d("ALBUM-RESPONSE", message)
        Log.d("POT", "fail")
    }

}
