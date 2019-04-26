package com.esi.dz_now

import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esi.dz_now.databinding.FragmentHomeBinding

class HomeFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container,false)

        val viewPager = binding.viewPager
        viewPager.adapter = ViewPagerAdapter(context!!)
        binding.slidingTabs.setupWithViewPager(viewPager)
        return binding.root
    }



}