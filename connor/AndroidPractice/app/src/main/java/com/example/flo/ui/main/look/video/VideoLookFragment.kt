package com.example.flo.ui.main.look.video

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentLookVideoBinding

class VideoLookFragment : Fragment() {
    lateinit var binding: FragmentLookVideoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLookVideoBinding.inflate(inflater, container, false)

        return binding.root
    }
}