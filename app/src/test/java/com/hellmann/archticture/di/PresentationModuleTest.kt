package com.hellmann.archticture.di

import com.hellmann.archticture.feature.list.ArticleViewModel
import com.hellmann.archticture.feature.list.ArticlesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 10/06/2019
 * 
 * (c) 2019 
 */

@ExperimentalCoroutinesApi
val presentationModuleTest = module {
    factory { ArticlesAdapter() }

    viewModel {
        ArticleViewModel(useCase = get(), ioDispatcher = TestCoroutineDispatcher())
    }
}
