package com.hellmann.archticture.di

import com.hellmann.archticture.feature.main.ArticlesAdapter
import com.hellmann.archticture.feature.main.MainViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val presentationModule = module {

    factory { ArticlesAdapter() }

    viewModel { MainViewModel(
        useCase = get(),
        uiScheduler = AndroidSchedulers.mainThread()
    ) }
}