package com.esi.dz_now.viewmodel
import androidx.lifecycle.MutableLiveData
import com.esi.dz_now.base.BaseViewModel
import com.esi.dz_now.model.ArticleModel


class ArticleViewModel: BaseViewModel() {

    private val articleId = MutableLiveData<String>()
    private val articleCategory = MutableLiveData<String>()
    private val articleContent = MutableLiveData<String>()
    private val articleDate = MutableLiveData<String>()
    private val articleImg = MutableLiveData<String>()
    private val articleSource = MutableLiveData<String>()
    private val articleTitle = MutableLiveData<String>()
    private val articleUrl = MutableLiveData<String>()
    private val articleIsFavorit = MutableLiveData<Boolean>()
    private val articleDateAndSource = MutableLiveData<String>()

    fun bind(article: ArticleModel){
        articleId.value = article.id
        articleCategory.value = article.category
        articleContent.value = article.content
        articleDate.value = article.date
        articleImg.value = article.img
        articleSource.value = article.source
        articleTitle.value = article.title
        articleUrl.value = article.url
        articleIsFavorit.value = article.favoris
        articleDateAndSource.value  = article.date + "|"+article.source
    }

    fun getArticleId(): MutableLiveData<String> {
        return articleId
    }

    fun getArticleCategory():MutableLiveData<String>{
        return articleCategory
    }

    fun getArticleContent():MutableLiveData<String>{
        return articleContent
    }

    fun getArticleDate():MutableLiveData<String>{
        return articleDate
    }

    fun getArticleImg():MutableLiveData<String>{
        return articleImg
    }

    fun getArticleSource():MutableLiveData<String>{
        return articleSource
    }

    fun getArticleTitle():MutableLiveData<String>{
        return articleTitle
    }

    fun getArticleUrl():MutableLiveData<String>{
        return articleUrl
    }

    fun getArticleIsFavorit(): MutableLiveData<Boolean>{
        return articleIsFavorit
    }

    fun getArticleDateAndSource():MutableLiveData<String>{
        return articleDateAndSource
    }
}