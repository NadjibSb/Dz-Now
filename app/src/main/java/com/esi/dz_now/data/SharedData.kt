package com.esi.dz_now.data

interface SharedData {
    fun getAllArticles(): MutableList<Article>
    fun getArticlesListByCategorie(categories: Categories): MutableList<Article>
    fun getAllCategories(): List<Categories>
    fun getCategories(): List<Categories>
    fun getArticleById(articleId: Int): Article
    fun getFavories():MutableList<Article>
}