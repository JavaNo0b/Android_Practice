package com.example.flo.ui.main.locker

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.ui.login.LoginActivity
import com.example.flo.databinding.FragmentLockerBinding
import com.example.flo.ui.main.MainActivity
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding

    private val information = arrayListOf("저장한 곡", "음악파일", "저장앨범")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        binding.storageSelectTv.setOnClickListener{
            setSelectStatus(false)
            val editbarDialog = FragmentEditbar(this)
            editbarDialog.show(childFragmentManager,editbarDialog.tag)

        }

        val lockerAdapter = LockerVPAdapter(this)
        binding.lockerContentVp.adapter = lockerAdapter
        TabLayoutMediator(binding.lockerContentTb, binding.lockerContentVp){
            tab, position ->
            tab.text = information[position]
        }.attach()

        binding.lockerLoginTv.setOnClickListener{
            startActivity(Intent(activity, LoginActivity::class.java))
        }

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }

    fun setSelectStatus(isPlaying : Boolean){
        if(isPlaying){
            binding.storageSelectImg.visibility = View.VISIBLE
            binding.storageSelectTv.visibility = View.VISIBLE
            binding.storageUnselectImg.visibility = View.INVISIBLE
            binding.storageUnselectTv.visibility = View.INVISIBLE
        }
        else{
            binding.storageSelectImg.visibility = View.INVISIBLE
            binding.storageSelectTv.visibility = View.INVISIBLE
            binding.storageUnselectImg.visibility = View.VISIBLE
            binding.storageUnselectTv.visibility = View.VISIBLE
        }
    }

    //jwt를 저장했던 auth로 가져오고 프래그먼트에서는 activity?를 사용한다.
    private fun getJwt():Int{
        val spf = activity?.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt",0) //가져온 값이 없다면 0을 반환
    }

    private fun initViews(){
        val jwt : Int = getJwt()
        if(jwt == 0){
            binding.lockerLoginTv.text = "로그인"
            binding.lockerLoginTv.setOnClickListener {
                startActivity(Intent(activity, LoginActivity::class.java))
            }
        }else{
            binding.lockerLoginTv.text = "로그아웃"
            binding.lockerLoginTv.setOnClickListener {
                logout()
                startActivity(Intent(activity, MainActivity::class.java))
            }
        }
    }
    private fun logout(){
        val spf = activity?.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("jwt") //jwt키 값의 데이터 지움
        editor.apply()
    }
}