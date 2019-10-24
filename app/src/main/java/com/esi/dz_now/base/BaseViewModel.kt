package com.esi.dz_now.base

import androidx.lifecycle.ViewModel
import com.esi.dz_now.injection.component.DaggerViewModelInjector
import com.esi.dz_now.injection.component.ViewModelInjector
import com.esi.dz_now.injection.module.NetworkModule
import com.esi.dz_now.viewmodel.ArticleViewModel
import com.esi.dz_now.viewmodel.ArticlesListViewModel
import com.esi.dz_now.viewmodel.SavedArticlesListViewModel

abstract class BaseViewModel: ViewModel() {

    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is ArticlesListViewModel -> injector.inject(this)
            is ArticleViewModel -> injector.inject(this)
            is SavedArticlesListViewModel -> injector.inject(this)

        }
    }
}