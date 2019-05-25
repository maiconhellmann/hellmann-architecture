package com.hellmann.data.local.source

import com.hellmann.data.local.database.ArticleDao
import com.hellmann.data.local.mapper.ArticleCacheMapper
import com.hellmann.domain.entity.Article
import io.reactivex.Single

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 25/05/2019
 * 
 * (c) 2019 
 */class ArticleCacheDataSourceImpl(private val articleDao: ArticleDao) : ArticleCacheDataSource {

    override fun getArticles(): Single<List<Article>> {
        return articleDao.getAll().map { ArticleCacheMapper.map(it) }
    }

    override fun insertData(list: List<Article>) {
        articleDao.insertAll(ArticleCacheMapper.mapToCache(list))
    }

    override fun updateData(list: List<Article>) {
        articleDao.updateDate(ArticleCacheMapper.mapToCache(list))
    }
}