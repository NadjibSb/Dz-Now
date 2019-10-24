package com.esi.dz_now.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ArticleDao {
    @get:Query("SELECT * FROM articleModel")
    val getArticles: List<ArticleModel>

    @Insert
    fun insertArticle(post: ArticleModel)


}