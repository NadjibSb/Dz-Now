package com.esi.dz_now.screens.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.esi.dz_now.R
import com.esi.dz_now.data.Categories
import com.esi.dz_now.databinding.FragmentHomeBinding
import com.esi.dz_now.screens.MainActivity
import com.esi.dz_now.viewmodel.ArticlesListViewModel
import com.google.android.material.snackbar.Snackbar


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: ArticlesListViewModel
    private var errorSnackbar: Snackbar? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_home, container, false
        )
        (activity as MainActivity).supportActionBar?.title = getString(R.string.home_fragment_title)
        viewModel = ViewModelProviders.of(this).get(ArticlesListViewModel::class.java)

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            if (errorMessage != null) showError(errorMessage) else hideError()
        })
        val viewPager = binding.viewPager
        viewPager.adapter = ViewPagerAdapter(context!!, getCategories(), viewModel, this)

        binding.slidingTabs.setupWithViewPager(viewPager)
        return binding.root
    }

    private fun showError(@StringRes errorMessage: Int) {
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction("Retry", viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError() {
        errorSnackbar?.dismiss()
    }


    private fun getCategories(): List<Categories> {
        var list = mutableListOf<Categories>()
        for (cat in Categories.values().toList()) {
            if (cat.isActivated) {
                list.add(cat)
            }
        }
        list.sortBy { categories ->
            categories.title
        }
        return list
    }


}