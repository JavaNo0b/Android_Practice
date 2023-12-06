package com.example.flo.ui.main.album.song

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.data.remote.albumsong.AlbumSongsService
import com.example.flo.data.remote.albumsong.Songs
import com.example.flo.databinding.FragmentSongBinding

class SongFragment(private val albumIdx: Int) : Fragment(), AlbumSongView {
    lateinit var binding: FragmentSongBinding
    private lateinit var albumSongsAdapter: AlbumSongsRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater,container, false)

        binding.albumMixOffIv.setOnClickListener {
            setMix(false)
        }
        binding.albumMixOnIv.setOnClickListener {
            setMix(true)
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getAlbumSongs(albumIdx)
    }

    fun setMix(isMixing : Boolean){
        if(isMixing){
            binding.albumMixOffIv.visibility = View.VISIBLE
            binding.albumMixOnIv.visibility = View.GONE
        } else{
            binding.albumMixOffIv.visibility = View.GONE
            binding.albumMixOnIv.visibility = View.VISIBLE
        }
    }

    private fun getAlbumSongs(albumIdx:Int) {
        val albumSongsService = AlbumSongsService()
        albumSongsService.setAlbumView(this)

        albumSongsService.getAlbumSongs(albumIdx)
    }

    private fun initRecyclerView(result: ArrayList<Songs>){
        albumSongsAdapter = AlbumSongsRVAdapter(requireContext(), result)

        binding.albumSongsMusicRv.adapter = albumSongsAdapter
    }

    override fun onGetAlbumLoading() {
        binding.albumSongsLoadingPb.visibility = View.VISIBLE
        Log.d("AlbumSongs","Load")
    }

    override fun onGetAlbumSuccess(code: Int, result: ArrayList<Songs>) {
        binding.albumSongsLoadingPb.visibility = View.GONE
        initRecyclerView(result)
        Log.d("AlbumSongs","Success")
    }

    override fun onGetAlbumFailure(message: String) {
        binding.albumSongsLoadingPb.visibility = View.GONE
        Log.d("SONG-FRAG/SONG-RESPONSE", message)
        Log.d("AlbumSongs","Failure")
    }
}