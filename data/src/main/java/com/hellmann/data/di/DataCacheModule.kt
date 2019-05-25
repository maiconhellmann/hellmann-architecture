package com.hellmann.data.di

import com.hellmann.data.local.database.ArticleDataBase
import com.hellmann.data.local.source.ArticleCacheDataSource
import com.hellmann.data.local.source.ArticleCacheDataSourceImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module.module

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 25/05/2019
 * 
 * (c) 2019 
 */
val cacheDataModule = module {
    single { ArticleDataBase.createDatabase(androidContext()) }
    factory<ArticleCacheDataSource> { ArticleCacheDataSourceImpl(articleDao = get()) }
}