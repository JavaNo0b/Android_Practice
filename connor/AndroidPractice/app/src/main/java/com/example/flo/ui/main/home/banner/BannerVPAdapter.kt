package com.example.flo.ui.main.home.banner

import android.renderscript.Float4
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import java.security.spec.MGF1ParameterSpec

class BannerVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    private val fragmentlist : ArrayList<Fragment> = ArrayList() //Viewpager에 들어갈 프래그먼트들의 리스트 선언

    override fun getItemCount(): Int { //전달할 데이터의 개수 // = fragmentlist.size 와 같은 표현
        return fragmentlist.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragmentlist[position] //getItemCount() 값 만큼 진행
    }

    fun addFragment(fragment: Fragment){
        fragmentlist.add(fragment)
        notifyItemInserted(fragmentlist.size-1) //ViewPager에게 fragmentlist의 size-1자리에 새로운 값이 추가되었다고 알림
    }
}