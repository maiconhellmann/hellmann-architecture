package com.hellmann.archticture.di

import com.hellmann.archticture.feature.list.ArticleViewModel
import com.hellmann.archticture.feature.list.ArticlesAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    factory { ArticlesAdapter() }

    viewModel { ArticleViewModel(
        useCase = get(),
        uiScheduler = AndroidSchedulers.mainThread()
    ) }
}