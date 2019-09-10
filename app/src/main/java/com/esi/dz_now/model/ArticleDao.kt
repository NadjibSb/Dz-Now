package com.esi.dz_now.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ArticleDao {
    @get:Query("SELECT * FROM articlemodel")
    val getArticles: List<ArticleModel>

    @Insert
    fun insertArticle(posts: ArticleModel)


}