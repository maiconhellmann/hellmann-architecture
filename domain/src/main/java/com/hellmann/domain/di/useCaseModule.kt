package com.hellmann.domain.di

import com.hellmann.domain.usecase.GetArticlesUseCase
import org.koin.dsl.module

val useCaseModule = module {

    factory {
        GetArticlesUseCase(
            repository = get())
    }
}

val domainModule = listOf(useCaseModule)
