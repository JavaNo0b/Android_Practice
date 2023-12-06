package com.example.flo.ui.main.album

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.ui.main.album.detail.DetailFragment
import com.example.flo.ui.main.album.song.SongFragment
import com.example.flo.ui.main.home.video.VideoFragment

class AlbumVPAdapter(fragment: Fragment, private val albumId: Int): FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> SongFragment(albumId)
            1 -> DetailFragment()
            else -> VideoFragment()
        }
    }
}