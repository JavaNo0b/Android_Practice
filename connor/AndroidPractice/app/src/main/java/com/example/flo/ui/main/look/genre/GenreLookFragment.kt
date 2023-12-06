package com.example.flo.ui.main.look.genre

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentLookGenreBinding

class GenreLookFragment : Fragment() {
    lateinit var binding: FragmentLookGenreBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLookGenreBinding.inflate(inflater, container, false)

        return binding.root
    }
}