package com.example.jetpackdemo.v

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.jetpackdemo.R
import com.example.jetpackdemo.adapter.GankAdapter
import com.example.jetpackdemo.databinding.GankFragmentBinding
import com.example.jetpackdemo.model.GankViewModel
import com.example.jetpackdemo.model.InjectorUtil

class GankFragment : Fragment() {

    private lateinit var viewModel: GankViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<GankFragmentBinding>(inflater, R.layout.gank_fragment, container, false)
        val factory = InjectorUtil.provideGankViewModelFactory()
        viewModel = ViewModelProviders.of(this, factory).get(GankViewModel::class.java)

        val adapter = GankAdapter()
        binding.recyclerView.adapter = adapter
        subsribeUi(adapter)

        return binding.root
    }

    private fun subsribeUi(adapter: GankAdapter) {
        viewModel.getGankDatas().observe(viewLifecycleOwner, Observer { datas ->
            if (datas != null) adapter.submitList(datas)
        })
    }

}
