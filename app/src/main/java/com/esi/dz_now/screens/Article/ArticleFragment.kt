package com.esi.dz_now.screens.Article


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.esi.dz_now.R
import com.esi.dz_now.data.SharedData
import com.esi.dz_now.databinding.FragmentArticleBinding


class ArticleFragment : Fragment() {

    private lateinit var data: SharedData

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentArticleBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_article,container,false)


        val args = ArticleFragmentArgs.fromBundle(arguments!!)
        data = activity as SharedData


        return binding.root
    }


}
