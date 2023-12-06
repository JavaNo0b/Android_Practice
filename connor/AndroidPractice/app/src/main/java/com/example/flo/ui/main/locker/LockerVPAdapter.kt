package com.example.flo.ui.main.locker

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.ui.main.locker.storage.StorageFragment
import com.example.flo.ui.main.locker.music.MusicFragment
import com.example.flo.ui.main.locker.savedalbum.SavedAlbumFragment

class LockerVPAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> StorageFragment()
            1 -> MusicFragment()
            else -> SavedAlbumFragment()
        }
    }

}