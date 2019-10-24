package com.esi.dz_now.viewmodel
import android.content.Context
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import com.esi.dz_now.R
import com.esi.dz_now.base.BaseViewModel
import com.esi.dz_now.model.ArticleModel
import com.esi.dz_now.network.ArticleApi
import com.esi.dz_now.screens.home.ArticleListAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import javax.inject.Inject


class ArticleViewModel: BaseViewModel() {


    val errorMessage: MutableLiveData<Int> = MutableLiveData()

    @Inject
    lateinit var ArticleApi: ArticleApi

    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()

    private lateinit var subscription: Disposable
    val errorClickListener = View.OnClickListener {  }


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

    fun loadArticleContent(source: String, url: String){

        subscription = ArticleApi.getArticleContent(com.esi.dz_now.network.ArticleApi.GetArticleContentBody(url, source))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveArticleContentStart() }
            .doOnTerminate { onRetrieveArticleContentFinish() }
            .subscribe(
                // Articled result
                { result -> onRetrieveArticleContentSuccess(result) },
                { error -> onRetrieveArticleContentError(error) }
            )
    }

    private fun onRetrieveArticleContentStart(){
        errorMessage.value = null
    }

    private fun onRetrieveArticleContentFinish(){
    }

    private fun onRetrieveArticleContentSuccess(content: ArticleModel){
        articleContent.value = content.content
    }

    private fun onRetrieveArticleContentError(error: Throwable){
        errorMessage.value = R.string.disabled
    }

    internal class InternetCheck(private val onInternetChecked: (Boolean) -> Boolean) :
        AsyncTask<Void, Void, Boolean>() {
        init {
            execute()
        }

        override fun doInBackground(vararg voids: Void): Boolean {
            return try {
                val sock = Socket()
                sock.connect(InetSocketAddress("8.8.8.8", 53), 1500)
                sock.close()
                true
            } catch (e: IOException) {
                false
            }

        }

        override fun onPostExecute(internet: Boolean) {
            onInternetChecked(internet)
        }
    }

}
