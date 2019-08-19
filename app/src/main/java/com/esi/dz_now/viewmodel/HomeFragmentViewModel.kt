package com.esi.dz_now.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.esi.dz_now.model.ApiRepo
import com.esi.dz_now.model.ApiResponse

class HomeFragmentViewModel(private val mApiRepo: ApiRepo) : ViewModel(){

    private var article : LiveData<ApiResponse>? = null

    fun getArticleData(refresh: Boolean): LiveData<ApiResponse>{
        if(refresh){
            article = null
        }
        if (this.article == null) {
            article = mApiRepo.getArticles()
            return article as LiveData<ApiResponse>
        }
        return article as LiveData<ApiResponse>
    }

    fun getArticleDataByCategory(refresh: Boolean, category: String): LiveData<ApiResponse>{
        if(refresh){
            article = null
        }
        if (this.article == null) {
            article = mApiRepo.getArticlesByCategory(category)
            return article as LiveData<ApiResponse>
        }
        return article as LiveData<ApiResponse>
    }
}