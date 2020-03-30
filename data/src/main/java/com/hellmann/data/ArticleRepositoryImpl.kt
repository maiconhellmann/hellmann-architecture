package com.hellmann.data

import com.hellmann.data.local.source.ArticleCacheDataSource
import com.hellmann.data.remote.source.RemoteDataSource
import com.hellmann.domain.entity.Article
import com.hellmann.domain.repository.ArticleRepository

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 25/05/2019
 * 
 * (c) 2019 
 */class ArticleRepositoryImpl(
    private val cacheDataSource: ArticleCacheDataSource,
    private val remoteDataSource: RemoteDataSource
) : ArticleRepository {
    override suspend fun getArticles(forceUpdate: Boolean): List<Article> {
        return if (forceUpdate) {
            getArticlesRemote(forceUpdate)
        } else {
            val articleList = cacheDataSource.getArticles()

            if (articleList.isEmpty()) {
                getArticlesRemote(true)
            } else {
                articleList
            }
        }
    }

    private suspend fun getArticlesRemote(forceUpdate: Boolean): List<Article> {
        return remoteDataSource.getArticles().also { articleList ->
            if (forceUpdate) {
                cacheDataSource.updateData(articleList)
            } else {
                cacheDataSource.insertData(articleList)
            }
        }
    }
}
