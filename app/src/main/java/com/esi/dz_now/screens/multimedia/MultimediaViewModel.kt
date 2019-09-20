package com.esi.dz_now.screens.multimedia

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.esi.dz_now.data.VideoModel
import com.esi.dz_now.injection.module.NetworkModule
import com.esi.dz_now.network.ArticleApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MultimediaViewModel : ViewModel() {

    val TAG = "TAG-MultimediaViewModel"
    private val api: ArticleApi

    init {
        api = NetworkModule.provideArticleApi(NetworkModule.provideRetrofitInterface())
    }

    fun getMultimediaList(): MutableLiveData<List<VideoModel>?> {

        var videos: List<VideoModel>?
        val list = MutableLiveData<List<VideoModel>?>()

        api.getMultimedia().enqueue(object : Callback<List<VideoModel>> {
            override fun onResponse(
                call: Call<List<VideoModel>>,
                response: Response<List<VideoModel>>
            ) {
                Log.i(TAG, "getMultimediaList: call enqueue")

                if (!response.isSuccessful) {
                    Log.i(TAG, "CODE:" + response.code().toString())
                    return
                }

                videos = response.body()  // Getting the list

                if (videos != null) {
                    Log.i(TAG, "REPONSES: Videos")

                    for (m in videos!!) {
                        var content = ""
                        content += " $m \n"
                        Log.i(TAG, "\n=========\n$content")
                    }
                    list.value = videos
                }
            }

            override fun onFailure(call: Call<List<VideoModel>>, t: Throwable) {
                Log.i(TAG, "error CODE:" + t.message)
                list.value = null
            }

        })

        return list
    }
}
