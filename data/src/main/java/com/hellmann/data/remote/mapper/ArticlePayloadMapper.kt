package com.hellmann.data.remote.mapper

import com.hellmann.data.remote.model.ArticlePayload
import com.hellmann.data.remote.model.ArticlesPayload
import com.hellmann.domain.entity.Article

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 25/05/2019
 * 
 * (c) 2019 
 */object ArticlePayloadMapper {

    fun map(payload: ArticlesPayload) = payload.articles.map { map(it) }

    private fun map(payload: ArticlePayload) = Article(
        title = payload.title,
        description = payload.description,
        publishedAt = payload.publishedAt,
        url = payload.url,
        urlToImage = payload.urlToImage)
}