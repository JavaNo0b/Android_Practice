package com.example.flo.ui.main.home.pannel

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.ui.main.home.pannel.PannelFragment
import com.example.flo.ui.main.home.pannel2.Pannel2Fragment

class PannelVPAdapter(fragment : Fragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> PannelFragment()
            else -> Pannel2Fragment()
        }
    }
}