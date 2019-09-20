package com.esi.dz_now.screens.multimedia

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.esi.dz_now.R
import com.esi.dz_now.data.VideoModel
import com.esi.dz_now.databinding.MultimediaFragmentBinding
import com.esi.dz_now.screens.MainActivity

class MultimediaFragment : Fragment() {


    private lateinit var viewModel: MultimediaViewModel
    private lateinit var binding: MultimediaFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.multimedia_fragment, container, false
        )
        (activity as MainActivity).supportActionBar?.title =
            getString(R.string.multimedia_fragment_title)
        (activity as MainActivity).supportActionBar?.show()
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MultimediaViewModel::class.java)



        viewModel.getMultimediaList().observe(this, Observer { videos ->
            binding.loadingView.visibility = View.GONE
            setupRecyclerView(videos)
        })

    }

    private fun setupRecyclerView(list: List<VideoModel>?) {
        var recyclerView = binding.videoRecycleView as RecyclerView
        recyclerView.adapter = VideoListAdapter(list)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.setHasFixedSize(true)
    }

}
