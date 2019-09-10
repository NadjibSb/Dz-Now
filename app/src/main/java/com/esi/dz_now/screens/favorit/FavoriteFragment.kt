package com.esi.dz_now.screens.favorit


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.StringRes
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esi.dz_now.R
import com.esi.dz_now.data.Article
import com.esi.dz_now.data.SharedData
import com.esi.dz_now.databinding.FragmentFavoriteBinding
import com.esi.dz_now.model.ArticleModel
import com.esi.dz_now.screens.MainActivity
import com.esi.dz_now.viewmodel.SavedArticlesListViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar


class FavoriteFragment : Fragment() {

    companion object {
        val instance = FavoriteFragment()
    }

    private lateinit var binding: FragmentFavoriteBinding
    private lateinit var viewModel: SavedArticlesListViewModel
    private var errorSnackbar: Snackbar? = null

    private var articlesList = ArrayList<ArticleModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        // val view = inflater.inflate(R.layout.fragment_ads, container, false)
        viewModel = ViewModelProviders.of(this).get(SavedArticlesListViewModel::class.java)

        viewModel.errorMessage.observe(this, Observer {
                errorMessage -> if(errorMessage != null) showError(errorMessage) else hideError()
        })

        binding.viewModel = viewModel

        //val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)

        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        //recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        /*  val rvAdapter = UsedCarAdapter(adsList, context!!)
          recyclerView.adapter = rvAdapter*/
        activity?.findViewById<BottomNavigationView>(R.id.nav_view)?.visibility = View.VISIBLE


        return binding.root
    }

    private fun showError(@StringRes errorMessage:Int){
        errorSnackbar = Snackbar.make(binding.root, errorMessage, Snackbar.LENGTH_INDEFINITE)
        errorSnackbar?.setAction("Retry", viewModel.errorClickListener)
        errorSnackbar?.show()
    }

    private fun hideError(){
        errorSnackbar?.dismiss()
    }




}


