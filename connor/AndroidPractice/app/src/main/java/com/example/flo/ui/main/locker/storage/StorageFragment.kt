package com.example.flo.ui.main.locker.storage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.flo.data.local.SongDatabase
import com.example.flo.data.entities.Song
import com.example.flo.databinding.FragmentStorageBinding

class StorageFragment : Fragment() {
    lateinit var binding: FragmentStorageBinding
    lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStorageBinding.inflate(inflater, container, false)

        songDB = SongDatabase.getInstance(requireContext())!!

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initRecyclerview()
    }

    private fun initRecyclerview(){
        binding.storageMusicRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        val storageRVAdapter = StorageRVAdapter()

        storageRVAdapter.setMyItemClickListener(object : StorageRVAdapter.MyItemClickListener {
            override fun onRemoveSong(songId: Int) {
                //[…] 버튼을 누르면 좋아요 목록에서 삭제되도록 구현
                songDB.songDao().updateIsLikeById(false, songId)
            }

        })

        binding.storageMusicRv.adapter = storageRVAdapter

        //true라면 보관함에 곡이 추가되도록 구현
        storageRVAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)


    }
}