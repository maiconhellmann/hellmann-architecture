package com.hellmann.data.remote.source

import com.hellmann.data.remote.api.ServerApi
import com.hellmann.data.remote.mapper.ArticlePayloadMapper
import com.hellmann.domain.entity.Article

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 25/05/2019
 * 
 * (c) 2019 
 */interface RemoteDataSource {
    suspend fun getArticles(): List<Article>
}

class RemoteDataSourceImpl(private val articleApi: ServerApi) : RemoteDataSource {
    override suspend fun getArticles(): List<Article> {
        return ArticlePayloadMapper.map(articleApi.fetchArticles())
    }
}
