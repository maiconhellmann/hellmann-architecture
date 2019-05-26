package com.hellmann.archticture

import android.app.Application
import com.hellmann.archticture.di.presentationModule
import com.hellmann.data.di.dataModules
import com.hellmann.domain.di.domainModule
import org.koin.android.ext.android.startKoin

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 26/05/2019
 * 
 * (c) 2019 
 */class PresentationApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(
            this@PresentationApplication,
            dataModules + domainModule + presentationModule
        )
    }
}