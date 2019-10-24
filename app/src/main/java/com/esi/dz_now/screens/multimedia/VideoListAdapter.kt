package com.esi.dz_now.screens.multimedia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.esi.dz_now.R
import com.esi.dz_now.data.VideoModel

class VideoListAdapter(val list: List<VideoModel>?) :
    RecyclerView.Adapter<VideoListAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        return VideoViewHolder.create(parent)
    }

    override fun getItemCount() = list?.size ?: 0

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        holder.bind(list?.get(position))
    }

    class VideoViewHolder private constructor(val layout: View) : RecyclerView.ViewHolder(layout) {

        val titleV: TextView = layout.findViewById(R.id.video_title)
        val sourceV: TextView = layout.findViewById(R.id.video_source)
        val dateV: TextView = layout.findViewById(R.id.video_date)
        val imageV: ImageView = layout.findViewById(R.id.video_image)

        fun bind(video: VideoModel?) {
            video?.apply {
                titleV.text = title
                sourceV.text = source
                dateV.text = date
                Glide.with(imageV.context)
                    .load(img)
                    .into(imageV)
                handleClick(layout, url)
            }
        }

        companion object {
            fun create(parent: ViewGroup): VideoViewHolder {
                val itemView = LayoutInflater.from(parent.context)
                    .inflate(R.layout.video_list_item, parent, false)
                return VideoViewHolder(itemView)
            }
        }


        private fun handleClick(view: View, url: String) {
            val action =
                MultimediaFragmentDirections.actionMultimediaFragmentToVideoDisplayFragment(url)
            view.setOnClickListener { v: View ->
                v.findNavController().navigate(action)
            }
        }

    }
}