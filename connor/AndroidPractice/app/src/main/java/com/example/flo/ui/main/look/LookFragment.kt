package com.example.flo.ui.main.look

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.flo.data.remote.song.FloChartResult
import com.example.flo.databinding.FragmentLookBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class LookFragment : Fragment(), LookView {

    lateinit var binding: FragmentLookBinding

    private val information = arrayListOf("차트", "영상", "장르", "상황", "분위기", "오디오")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLookBinding.inflate(inflater, container, false)

        val lookAdapter = LookVPAdapter(this)
        binding.lookContentVp.adapter = lookAdapter
        TabLayoutMediator(binding.lookTabTl, binding.lookContentVp){
                tab, position ->
            tab.text = information[position]
        }.attach()

        setTabItemMargin(binding.lookTabTl, 30)

        return binding.root
    }

    // TabLayout Tab 사이 간격 부여
    private fun setTabItemMargin(tabLayout: TabLayout, marginEnd: Int = 20) {
        for(i in 0 until 6) {
            val tabs = tabLayout.getChildAt(0) as ViewGroup
            for(i in 0 until tabs.childCount) {
                val tab = tabs.getChildAt(i)
                val lp = tab.layoutParams as LinearLayout.LayoutParams
                lp.marginEnd = marginEnd
                tab.layoutParams = lp
                tabLayout.requestLayout()
            }
        }
    }



    override fun onGetSongLoading() {

    }

    override fun onGetSongSuccess(code: Int, result: FloChartResult) {
        TODO("Not yet implemented")
    }

    override fun onGetSongFailure(code: Int, message: String) {
        TODO("Not yet implemented")
    }
}



