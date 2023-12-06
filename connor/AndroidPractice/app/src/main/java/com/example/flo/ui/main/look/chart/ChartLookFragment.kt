package com.example.flo.ui.main.look.chart

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.ui.main.look.LookView
import com.example.flo.data.remote.song.FloChartResult
import com.example.flo.data.remote.song.SongService
import com.example.flo.databinding.FragmentLookChartBinding

class ChartLookFragment : Fragment(), LookView {
    lateinit var binding: FragmentLookChartBinding
    lateinit var floChartAdapter: SongRVAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLookChartBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getSongs()
    }

    private fun initRecyclerView(result: FloChartResult){
        floChartAdapter = SongRVAdapter(requireContext(), result)

        binding.chartMusicRv.adapter = floChartAdapter
    }

    private fun getSongs() {
        val songService = SongService()
        songService.setLookView(this)

        songService.getSongs()
    }


    override fun onGetSongLoading() {
        binding.chartLoadingPb.visibility = View.VISIBLE
    }

    override fun onGetSongSuccess(code: Int, result: FloChartResult) {
        binding.chartLoadingPb.visibility = View.GONE
        initRecyclerView(result)
    }

    override fun onGetSongFailure(code: Int, message: String) {
        binding.chartLoadingPb.visibility = View.GONE
        Log.d("LOOK-FRAG/SONG-RESPONSE", message)
    }
}