package com.esi.dz_now.screens.Favorit


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.esi.dz_now.R
import com.esi.dz_now.databinding.FragmentFavoriteBinding
import com.esi.dz_now.screens.MainActivity


class FavoriteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentFavoriteBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.favourites_fragment_title)

        return binding.root
    }


}


