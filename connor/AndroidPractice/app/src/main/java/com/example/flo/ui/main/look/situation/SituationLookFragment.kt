package com.example.flo.ui.main.look.situation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentLookSituationBinding

class SituationLookFragment : Fragment() {
    lateinit var binding: FragmentLookSituationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLookSituationBinding.inflate(inflater, container, false)

        return binding.root
    }
}