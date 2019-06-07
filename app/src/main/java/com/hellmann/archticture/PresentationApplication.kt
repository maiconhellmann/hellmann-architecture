package com.hellmann.archticture

import android.app.Application
import com.hellmann.archticture.di.presentationModule
import com.hellmann.data.di.dataModules
import com.hellmann.domain.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 26/05/2019
 * 
 * (c) 2019 
 */class PresentationApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // declare used Android context
            androidContext(this@PresentationApplication)
            // declare modules
            modules(dataModules + domainModule + presentationModule)
        }
    }
}