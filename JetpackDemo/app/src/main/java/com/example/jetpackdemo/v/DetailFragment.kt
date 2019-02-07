package com.example.jetpackdemo.v

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.jetpackdemo.R
import com.example.jetpackdemo.databinding.DetailFragmentBinding

class DetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<DetailFragmentBinding>(inflater, R.layout.detail_fragment, container, false)
        binding.webView.loadUrl(arguments?.getString("url"))
        return binding.root
    }

}
