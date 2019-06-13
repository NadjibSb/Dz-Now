package com.esi.dz_now.data

import java.util.*

class DataUtil {

    private lateinit var articlesList: MutableList<Article>

    init {
        creatArticleList()
    }

    fun getCategories(): List<Categories>{
        var list= mutableListOf<Categories>()
        for (cat in Categories.values().toList()){
            if (cat.isActivated){
                list.add(cat)
            }
        }
        return list
    }

    fun getAllCategories():List<Categories>{
        return Categories.values().toList()
    }

    fun getArticlesListByCategorie(categories: Categories): MutableList<Article>? {
        var list = getAllArticlesByCategories(articlesList)
        return list[categories]
    }

    fun getAllArticles(): MutableList<Article> {
        articlesList.sortByDescending { article ->
            article.date
        }
        return articlesList
    }


    private fun creatArticleList() {
        val articles = mutableListOf<Article>()

        for (categorie in Categories.values()) {
            for (i in 0..5) {
                articles.add(
                    Article(
                        i,
                        "article de ${categorie.title.toString()} ${i}",
                        "",
                        "Ceci est le contenu d'un article sur la ${categorie.title.toString()}",
                        categorie,
                        Date(),
                        true
                    )
                )
            }
        }
        articlesList = articles
    }

    private fun getAllArticlesByCategories(ArticalesList: MutableList<Article>): MutableMap<Categories, MutableList<Article>> {

        val articlesListByCategories: MutableMap<Categories, MutableList<Article>> =
            ArticalesList.groupByTo(mutableMapOf()) { it.categories }
        return articlesListByCategories
    }

    fun getArticleById(articleId: Int): Article {
        var i = 0
        var article = articlesList[0]
        while (i < articlesList.size) {
            if (articlesList[i].id == articleId) {
                article = articlesList[i]
            }
            i++
        }
        return article
    }


}