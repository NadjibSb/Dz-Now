package com.esi.dz_now.network

import com.esi.dz_now.model.ArticleModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {
    /**
     * Get the list of articles from the API
     */
    @GET("/api/articles")
    fun getArticles(@Query("category")category: String): Observable<List<ArticleModel>>





}