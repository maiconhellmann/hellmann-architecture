package com.hellmann.archticture.di

import com.hellmann.archticture.feature.list.ArticlesAdapter
import com.hellmann.archticture.feature.list.ArticleViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val presentationModule = module {

    factory { ArticlesAdapter() }

    viewModel { ArticleViewModel(
        useCase = get(),
        uiScheduler = AndroidSchedulers.mainThread()
    ) }
}