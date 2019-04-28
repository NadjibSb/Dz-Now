package com.esi.dz_now.screens.Home

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.esi.dz_now.R
import com.esi.dz_now.data.Article
import com.esi.dz_now.data.SharedData
import com.esi.dz_now.databinding.FragmentHomeBinding
import com.esi.dz_now.screens.MainActivity

class HomeFragment: Fragment() {

    private lateinit var data: SharedData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_home, container,false)
        data = this.activity as SharedData


        (activity as MainActivity).supportActionBar?.title = getString(R.string.home_fragment_title)

        val viewPager = binding.viewPager
        viewPager.adapter = ViewPagerAdapter(context!!,data.getAllArticles(),data.getCategories())
        binding.slidingTabs.setupWithViewPager(viewPager)
        return binding.root
    }
}