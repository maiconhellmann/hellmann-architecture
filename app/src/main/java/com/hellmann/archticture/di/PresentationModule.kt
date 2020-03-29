package com.hellmann.archticture.di

import com.hellmann.archticture.feature.list.ArticleViewModel
import com.hellmann.archticture.feature.list.ArticlesAdapter
import kotlinx.coroutines.Dispatchers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {

    factory { ArticlesAdapter() }

    viewModel {
        ArticleViewModel(
            useCase = get(), ioDispatcher = Dispatchers.IO)
    }
}
