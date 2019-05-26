package com.hellmann.data.di

import com.hellmann.data.BuildConfig
import com.hellmann.data.remote.api.ServerApi
import com.hellmann.data.remote.api.interceptor.AuthenticationRequestInterceptor
import com.hellmann.data.remote.source.RemoteDataSource
import com.hellmann.data.remote.source.RemoteDataSourceImpl
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 25/05/2019
 * 
 * (c) 2019 
 */
val remoteDataSourceModule = module {
    factory { providesOkHttpClient(interceptor = get()) }
    factory { provideAuthenticationRequestInterceptor() }
    single { createWebService<ServerApi>(
        okHttpClient = get(),
        url =  BuildConfig.BASE_URL
    ) }

    factory<RemoteDataSource> { RemoteDataSourceImpl(articleApi = get()) }
}

fun provideAuthenticationRequestInterceptor() = AuthenticationRequestInterceptor()

fun providesOkHttpClient(
    interceptor: AuthenticationRequestInterceptor
): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()
}

inline fun <reified T> createWebService(
    okHttpClient: OkHttpClient,
    url: String
): T {
    return Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .baseUrl(url)
        .client(okHttpClient)
        .build()
        .create(T::class.java)
}