package com.example.flo.ui.main.look.atmos

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentLookAtmosBinding

class AtmosLookFragment : Fragment() {
    lateinit var binding: FragmentLookAtmosBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLookAtmosBinding.inflate(inflater, container, false)

        return binding.root
    }
}