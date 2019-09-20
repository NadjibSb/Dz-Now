package com.esi.dz_now.screens.videoDisplay

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.esi.dz_now.R
import com.esi.dz_now.screens.MainActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import kotlinx.android.synthetic.main.video_display_fragment.*

class VideoDisplayFragment : Fragment() {

    private var TAG = "TAG-VideoDisplayFragment"
    private lateinit var viewModel: VideoDisplayViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.video_display_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (activity as MainActivity).supportActionBar?.hide()
        viewModel = ViewModelProviders.of(this).get(VideoDisplayViewModel::class.java)

        val videoUrl = VideoDisplayFragmentArgs.fromBundle(arguments!!).url
        lifecycle.addObserver(youtube_video)

        youtube_video.enterFullScreen()

        viewModel.getVideo(videoUrl).observe(this, Observer { u->
            if (u != null){
                val url = u.split("/").last().split("?").first()

                Log.i(TAG,url)
                youtube_video.addYouTubePlayerListener(object : AbstractYouTubePlayerListener(){
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.loadVideo(url,0F)
                    }
                })
            }
        })
    }

}
