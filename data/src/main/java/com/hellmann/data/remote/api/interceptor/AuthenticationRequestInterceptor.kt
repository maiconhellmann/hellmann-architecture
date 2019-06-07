package com.hellmann.data.remote.api.interceptor

import com.hellmann.data.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 26/05/2019
 * 
 * (c) 2019 
 */class AuthenticationRequestInterceptor : Interceptor {

    companion object {
        const val KEY_AUTHENTICATION = "X-Api-Key"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain.request().newBuilder().addHeader(KEY_AUTHENTICATION, BuildConfig.API_KEY).build()
        return chain.proceed(request)
    }
}