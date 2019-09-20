package com.esi.dz_now.injection.component

import com.esi.dz_now.injection.module.NetworkModule
import com.esi.dz_now.viewmodel.ArticleViewModel
import com.esi.dz_now.viewmodel.ArticlesListViewModel
import com.esi.dz_now.viewmodel.SavedArticlesListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified AdsListViewModel.
     * @param articlesListViewModel ArticlesListViewModel in which to inject the dependencies
     */
    fun inject(articlesListViewModel: ArticlesListViewModel)

    fun inject(articleViewModel: ArticleViewModel)

    fun inject(savedArticlesListViewModel: SavedArticlesListViewModel)


    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}