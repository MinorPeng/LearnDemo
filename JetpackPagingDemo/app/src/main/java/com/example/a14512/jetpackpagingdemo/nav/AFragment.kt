package com.example.a14512.jetpackpagingdemo.nav

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.a14512.jetpackpagingdemo.R
import kotlinx.android.synthetic.main.fragment_a.*

class AFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        btn_a_to_b.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_AFragment_to_BFragment)
        }
        //或者
//        btn_a_to_b.setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_AFragment_to_BFragment))
    }

}
