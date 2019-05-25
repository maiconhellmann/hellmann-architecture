package com.hellmann.data

import com.hellmann.data.local.source.ArticleCacheDataSource
import com.hellmann.data.remote.source.RemoteDataSource
import com.hellmann.domain.entity.Article
import com.hellmann.domain.repository.ArticleRepository
import io.reactivex.Single

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
    override fun getArticles(forceUpdate: Boolean): Single<List<Article>> {
        return if (forceUpdate) {
            getArticlesRemote(forceUpdate)
        } else {
            cacheDataSource.getArticles().flatMap { articleList ->
                when {
                    articleList.isEmpty() -> getArticlesRemote(false)
                    else -> Single.just(articleList)
                }
            }
        }
    }

    private fun getArticlesRemote(forceUpdate: Boolean): Single<List<Article>> {
        return remoteDataSource.getArticles().flatMap { articleList ->
            if (forceUpdate) {
                cacheDataSource.updateData(articleList)
            } else {
                cacheDataSource.insertData(articleList)
            }
            Single.just(articleList)
        }
    }
}