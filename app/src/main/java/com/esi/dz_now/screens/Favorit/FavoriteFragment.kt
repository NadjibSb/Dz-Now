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


class FavoriteFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentFavoriteBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)

        binding.button.setOnClickListener {
            it.findNavController().navigate(R.id.action_favoriteFragment_to_articleFragment)
        }

        Glide.with(context!!)
            .load("http://www.onisep.fr/var/onisep/storage/images/media/regions/centre/images/a-se-former-dans-ma-region/sport-100x100/18288753-1-fre-FR/Sport-100x100.jpg")
            .centerCrop()
            .placeholder(R.drawable.ic_menu_gallery)
            .to(binding.root.findViewById(R.id.imagetest) as ImageView)
        binding.invalidateAll()
        return binding.root
    }


}


