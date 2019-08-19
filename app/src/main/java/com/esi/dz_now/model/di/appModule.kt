package com.esi.dz_now.model.di

import com.esi.dz_now.model.ApiRepo
import com.esi.dz_now.viewmodel.HomeFragmentViewModel
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {

    // single instance of HelloRepository
    single { ApiRepo() }

    // MyViewModel ViewModel
    viewModel{HomeFragmentViewModel(get())}
}