package com.esi.dz_now.model

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.esi.dz_now.model.service.ApiClient
import com.esi.dz_now.model.service.ApiInterface

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class ApiRepo{

    var endpoints : ApiClient = ApiClient()
    var mCompositeDisposable = CompositeDisposable()
    val apiResponse = MutableLiveData<ApiResponse>()
    fun getArticles() : LiveData<ApiResponse> {


        val apiService = endpoints.getClient()!!.create(ApiInterface::class.java)
        mCompositeDisposable?.add(apiService.getArticlesByCategory("POLITICS")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse, this::handleError))
        return apiResponse
    }

    fun getArticlesByCategory(category: String) : LiveData<ApiResponse> {


        val apiService = endpoints.getClient()!!.create(ApiInterface::class.java)
        mCompositeDisposable?.add(apiService.getArticlesByCategory(category)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(this::handleResponse, this::handleError))
        return apiResponse
    }

    fun handleResponse(articles_list: List<ArticleModel>) {
        apiResponse.postValue(ApiResponse(articles_list))

    }

    private fun handleError(error: Throwable) {

        Log.d("throwable", error.localizedMessage)

        //Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }
}
