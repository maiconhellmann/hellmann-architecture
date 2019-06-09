package com.hellmann.data.di

import com.hellmann.data.local.database.ArticleDataBase
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.dsl.koinApplication
import org.koin.dsl.module

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 09/06/2019
 * 
 * (c) 2019 
 */

val cacheDataModuleTest = module {
    single { ArticleDataBase.createDatabaseInMemory(androidContext()) }
}
