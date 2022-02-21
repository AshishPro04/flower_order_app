package com.example.flowera

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.flowera.databinding.FragmentAboutUsBinding


class AboutUsFragment : Fragment() {
    private lateinit var aboutUsBinding: FragmentAboutUsBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aboutUsBinding = FragmentAboutUsBinding.inflate(inflater, container, false)
        return aboutUsBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aboutUsBinding?.apply {
            lifecycleOwner = viewLifecycleOwner
            aboutUsFragment = this@AboutUsFragment
        }
    }

    fun backPress() {
        val action = AboutUsFragmentDirections.actionAboutUsFragmentToStartFragment()
        findNavController().navigate(action)
    }

}