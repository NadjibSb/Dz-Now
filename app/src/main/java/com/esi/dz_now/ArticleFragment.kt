package com.esi.dz_now


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.esi.dz_now.databinding.FragmentArticleBinding


class ArticleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentArticleBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_article,container,false)

        return binding.root
    }


}
