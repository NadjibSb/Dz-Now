package com.esi.dz_now.screens.videoDisplay

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esi.dz_now.data.VideoUrl
import com.esi.dz_now.injection.module.NetworkModule
import com.esi.dz_now.network.ArticleApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VideoDisplayViewModel : ViewModel() {

    private val TAG = "TAG-VideoDisplayVM"
    private val api: ArticleApi

    init {
        api = NetworkModule.provideArticleApi(NetworkModule.provideRetrofitInterface())
    }

    fun getVideo(url: String): MutableLiveData<String?> {
        var videoUrl: VideoUrl?
        val video = MutableLiveData<String?>()

        val u = VideoUrl(url, "")

        api.getVideoUrl(u).enqueue(object : Callback<VideoUrl?> {
            override fun onFailure(call: Call<VideoUrl?>, t: Throwable) {
                Log.i(TAG, "error CODE:" + t.message)
            }

            override fun onResponse(call: Call<VideoUrl?>, response: Response<VideoUrl?>) {
                Log.i(TAG, "getVideo: call enqueue")

                if (!response.isSuccessful) {
                    Log.i(TAG, "CODE:" + response.code().toString())
                    return
                }

                videoUrl = response.body()  // Getting the list

                if (videoUrl != null) {
                    Log.i(TAG, "REPONSES: Video url")
                    Log.i(TAG, "\n=========\n$videoUrl")
                    video.value = videoUrl?.videoUrl
                }
            }
        })
        return video
    }
}
