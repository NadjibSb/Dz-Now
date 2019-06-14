package com.esi.dz_now.screens.Favorit


import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esi.dz_now.R
import com.esi.dz_now.data.Article
import com.esi.dz_now.data.SharedData
import com.esi.dz_now.databinding.FragmentFavoriteBinding
import com.esi.dz_now.screens.MainActivity




class FavoriteFragment : Fragment() {

    private lateinit var data: SharedData


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val binding: FragmentFavoriteBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        (activity as MainActivity).supportActionBar?.title = getString(R.string.favourites_fragment_title)
        data = this.activity as SharedData

        setUpRecycleView(binding.root, data.getFavories())

        return binding.root
    }

    //RecycleView--------------------------------------------
    private fun setUpRecycleView(rootView: View, list: MutableList<Article>) {
        var recyclerView = rootView.findViewById(R.id.recycleView) as RecyclerView


        var adapter = FavorisArticleListAdapter(list, context!!)
        adapter.itemChanged.observe(this, Observer { state ->
            if (state){
                adapter.notifyDataSetChanged()
                adapter.itemChanged.value = false
            }
        })
        recyclerView.adapter = adapter

        val screenOrientation =
            (context?.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.orientation
        when (screenOrientation) {
            Surface.ROTATION_0 -> recyclerView.layoutManager = LinearLayoutManager(context)
            else -> recyclerView.layoutManager = GridLayoutManager(context, 2)
        }
        recyclerView.setHasFixedSize(true)
    }



}


