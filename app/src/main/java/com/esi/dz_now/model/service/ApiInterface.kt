package com.esi.dz_now.model.service

import com.esi.dz_now.model.ArticleModel
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/api/articles")
    fun getArticles(
        @Query("category")category: String

    ): Observable<List<ArticleModel>>
}