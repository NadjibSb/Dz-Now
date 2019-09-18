package com.esi.dz_now.network

import com.esi.dz_now.data.VideoModel
import com.esi.dz_now.data.VideoUrl
import com.esi.dz_now.model.ArticleModel
import io.reactivex.Observable
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*
import java.util.*

interface ArticleApi {
    /**
     * Get the list of articles from the API
     */
    @GET("/api/articles")
    fun getArticles(@Query("category") category: String): Observable<List<ArticleModel>>


    /**
     * Get article content from the API
     */
    @POST("/api/articleContent")
    fun getArticleContent(@Body body: GetArticleContentBody): Observable<ArticleModel>

    class GetArticleContentBody(
        var url: String,
        var source: String
    )

    @GET("api/videos")
    fun getMultimedia(): Call<List<VideoModel>>


    @POST("api/videoUrl")
    fun getVideoUrl(@Body url: VideoUrl): Call<VideoUrl?>


}