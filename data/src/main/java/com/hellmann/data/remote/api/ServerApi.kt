package com.hellmann.data.remote.api

import com.hellmann.data.remote.model.ArticlesPayload
import io.reactivex.Single
import retrofit2.http.GET

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 25/05/2019
 * 
 * (c) 2019 
 */interface ServerApi {

    @GET("/v2/top-headlines?country=gb&apiKey=7a0d194a8eb34ca190e574d870f87fd7")
    fun fetchArticles(): Single<ArticlesPayload>
}