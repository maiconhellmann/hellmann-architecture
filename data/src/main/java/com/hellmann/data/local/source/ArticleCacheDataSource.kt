package com.hellmann.data.local.source

import com.hellmann.data.local.database.ArticleDao
import com.hellmann.data.local.mapper.ArticleCacheMapper
import com.hellmann.domain.entity.Article

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 25/05/2019
 * 
 * (c) 2019 
 */
interface ArticleCacheDataSource {
    suspend fun getArticles(): List<Article>
    suspend fun insertData(list: List<Article>)
    suspend fun updateData(list: List<Article>)
}

class ArticleCacheDataSourceImpl(private val articleDao: ArticleDao) : ArticleCacheDataSource {

    override suspend fun getArticles(): List<Article> {
        return articleDao.getAll().map { ArticleCacheMapper.map(it) }
    }

    override suspend fun insertData(list: List<Article>) {
        articleDao.insertAll(ArticleCacheMapper.mapToCache(list))
    }

    override suspend fun updateData(list: List<Article>) {
        articleDao.updateDate(ArticleCacheMapper.mapToCache(list))
    }
}
