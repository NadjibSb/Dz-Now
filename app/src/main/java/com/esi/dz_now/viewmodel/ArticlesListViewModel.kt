package com.esi.dz_now.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.esi.dz_now.R
import com.esi.dz_now.base.BaseViewModel
import com.esi.dz_now.model.ArticleModel
import com.esi.dz_now.network.ArticleApi
import com.esi.dz_now.screens.home.ArticleListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ArticlesListViewModel: BaseViewModel(){

    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    lateinit var articleContent: ArticleModel
    @Inject
    lateinit var ArticleApi: ArticleApi

    val articlesListadapter: ArticleListAdapter = ArticleListAdapter()

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    private lateinit var subscription: Disposable
    val errorClickListener = View.OnClickListener {  }


    fun loadArticles(category: String){
        subscription = ArticleApi.getArticles(category)
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

    private fun onRetrieveArticlesListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveArticlesListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveArticlesListSuccess(articlesList:List<ArticleModel>){
        loadingVisibility.value = View.GONE
        articlesListadapter.updateArticlesList(articlesList)
    }

    private fun onRetrieveArticlesListError(error: Throwable){
        Log.e("throwable", error.localizedMessage)
        errorMessage.value = R.string.disabled
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }


}