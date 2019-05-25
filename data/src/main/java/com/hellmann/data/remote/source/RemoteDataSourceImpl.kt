package com.hellmann.data.remote.source

import com.hellmann.data.remote.mapper.ArticlePayloadMapper
import com.hellmann.data.remote.api.ServerApi
import com.hellmann.domain.entity.Article
import io.reactivex.Single

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 25/05/2019
 * 
 * (c) 2019 
 */class RemoteDataSourceImpl(private val articleApi: ServerApi) : RemoteDataSource {
    override fun getArticles(): Single<List<Article>> {
        return articleApi.fetchArticles().map { ArticlePayloadMapper.map(it) }
    }
}