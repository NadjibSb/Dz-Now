package com.esi.dz_now.viewmodel

import android.content.Context
import android.net.ConnectivityManager
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.esi.dz_now.R
import com.esi.dz_now.base.BaseViewModel
import com.esi.dz_now.data.Article
import com.esi.dz_now.model.ArticleDao
import com.esi.dz_now.model.ArticleModel
import com.esi.dz_now.network.ArticleApi
import com.esi.dz_now.screens.favorit.FavorisArticleListAdapter
import com.esi.dz_now.screens.home.ArticleListAdapter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class SavedArticlesListViewModel(private val articleDao: ArticleDao): BaseViewModel(){


    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    var userId_ = "DKxJgCwfRchGVLaSJfUelOER4iY2"
    @Inject
    lateinit var ArticleApi: ArticleApi

    val articlesListAdapter: FavorisArticleListAdapter = FavorisArticleListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    private lateinit var subscription: Disposable
    val errorClickListener = View.OnClickListener {  }



    fun loadSavedArticles(){
        subscription = Observable.fromCallable { articleDao.getArticles }
            .concatMap {
                    dbArticlesList -> Observable.just(dbArticlesList)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveArticlesListStart() }
            .doOnTerminate { onRetrieveArticlesListFinish() }
            .subscribe(
                // Articled result
                { result -> onRetrieveArticlesListSuccess(result) },
                { error -> onRetrieveArticlesListError(error) }
            )
    }

    fun loadSavedOnlineArticles(userId:String){
        subscription = ArticleApi.getSavedArticles(userId_)

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveArticlesListStart() }
            .doOnTerminate { onRetrieveArticlesListFinish() }
            .subscribe(
                // Articled result
                { result -> onRetrieveArticlesOnlineListSuccess(result) },
                { error -> onRetrieveArticlesListError(error) }
            )
    }
    private fun onRetrieveArticlesOnlineListSuccess(articlesList:List<ArticleApi.SaveArticleBody>){
        var list = mutableListOf<ArticleModel>()
        for(article in articlesList){
            list.add(ArticleModel("",article.title,article.url, article.content, article.img, article.category,article.date, article.source, true))
        }
        articlesListAdapter.updateArticlesList(list)
    }
    private fun onRetrieveArticlesListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveArticlesListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveArticlesListSuccess(articlesList:List<ArticleModel>){
        articlesListAdapter.updateArticlesList(articlesList)
    }


    private fun onRetrieveArticlesListError(error: Throwable){
        Log.e("throwable", error.localizedMessage)
        errorMessage.value = R.string.disabled
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun saveArticle(article: ArticleModel){

        Log.e("Article", article.toString())

        subscription = Observable.fromCallable { articleDao.insertArticle(article)}
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveArticlesListStart() }
            .doOnTerminate { onRetrieveArticlesListFinish() }
            .subscribe()
    }

    fun saveArticleOnline(userId: String, article:ArticleModel){

        var articleBody = com.esi.dz_now.network.ArticleApi.SaveArticleBody(
            userId_,
            article.title,
            article.content,
            article.source,
            article.category,
            article.img,
            article.date,
            article.url
        )
        subscription = ArticleApi.saveArticle(articleBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveArticlesListStart() }
            .doOnTerminate { onRetrieveArticlesListFinish() }
            .subscribe()
    }
}