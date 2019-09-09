package com.esi.dz_now.injection.component

import com.esi.dz_now.injection.module.NetworkModule
import com.esi.dz_now.viewmodel.ArticlesListViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified AdsListViewModel.
     * @param adListViewModel PostListViewModel in which to inject the dependencies
     */
    fun inject(articlesListViewModel: ArticlesListViewModel)


    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}