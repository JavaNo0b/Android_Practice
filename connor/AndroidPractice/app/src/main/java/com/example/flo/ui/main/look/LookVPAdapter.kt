package com.example.flo.ui.main.look

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.flo.ui.main.look.atmos.AtmosLookFragment
import com.example.flo.ui.main.look.audio.AudioLookFragment
import com.example.flo.ui.main.look.chart.ChartLookFragment
import com.example.flo.ui.main.look.genre.GenreLookFragment
import com.example.flo.ui.main.look.situation.SituationLookFragment
import com.example.flo.ui.main.look.video.VideoLookFragment

class LookVPAdapter(fragment : Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 6

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> ChartLookFragment()
            1 -> VideoLookFragment()
            2 -> GenreLookFragment()
            3 -> SituationLookFragment()
            4 -> AtmosLookFragment()
            else -> AudioLookFragment()
        }
    }

}