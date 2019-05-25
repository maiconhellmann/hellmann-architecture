package com.hellmann.data.di

import com.hellmann.data.ArticleRepositoryImpl
import com.hellmann.domain.repository.ArticleRepository
import org.koin.dsl.module.module

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 25/05/2019
 * 
 * (c) 2019 
 */
val repositoryModule = module {
    factory<ArticleRepository> {
        ArticleRepositoryImpl(
            cacheDataSource = get(), remoteDataSource = get())
    }
}

val dataModules = listOf(remoteDataSourceModule, repositoryModule, cacheDataModule)