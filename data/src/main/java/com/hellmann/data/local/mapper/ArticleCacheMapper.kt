package com.hellmann.data.local.mapper

import com.hellmann.data.local.model.ArticleCache
import com.hellmann.domain.entity.Article

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 25/05/2019
 * 
 * (c) 2019 
 */

object ArticleCacheMapper {
    fun map(cacheData: List<ArticleCache>) = cacheData.map { map(it) }

    private fun map(cacheData: ArticleCache) = Article(
        title = cacheData.title,
        description = cacheData.description,
        publishedAt = cacheData.publishedAt,
        url = cacheData.url,
        urlToImage = cacheData.urlToImage)

    fun mapToCache(articles: List<Article>) = articles.map { map(it) }

    private fun map(article: Article) = ArticleCache(
        title = article.title,
        description = article.description,
        urlToImage = article.urlToImage,
        url = article.url,
        publishedAt = article.publishedAt)
}