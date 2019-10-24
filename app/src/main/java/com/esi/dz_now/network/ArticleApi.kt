package com.esi.dz_now.network

import com.esi.dz_now.data.VideoModel
import com.esi.dz_now.data.VideoUrl
import com.esi.dz_now.model.ArticleModel
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.*

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

    @GET("api/saved-articles/{userId}")
    fun getSavedArticles(@Path("userId") userId: String): Observable<List<SaveArticleBody>>

    @POST("/api/saved-articles")
    fun saveArticle(@Body body: SaveArticleBody): Observable<SaveArticleBody>

    class SaveArticleBody(
        var userId: String,
        var title: String,
        var content: String,
        var source: String,
        var category: String,
        var img: String,
        var date: String,
        var url: String
    )
}