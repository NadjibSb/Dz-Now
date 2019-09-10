package com.esi.dz_now.network

import com.esi.dz_now.model.ArticleModel
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ArticleApi {
    /**
     * Get the list of articles from the API
     */
    @GET("/api/articles")
    fun getArticles(@Query("category")category: String): Observable<List<ArticleModel>>


    /**
     * Get article content from the API
     */
    @POST("/api/articleContent")
    fun getArticleContent(@Body body: GetArticleContentBody): Observable<ArticleModel>

    class GetArticleContentBody(
        var url: String,
        var source: String
    )





}