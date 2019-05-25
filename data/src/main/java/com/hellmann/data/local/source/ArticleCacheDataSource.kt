package com.hellmann.data.local.source

import com.hellmann.domain.entity.Article
import io.reactivex.Single

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 25/05/2019
 * 
 * (c) 2019 
 */
interface ArticleCacheDataSource {
    fun getArticles(): Single<List<Article>>
    fun insertData(list: List<Article>)
    fun updateData(list: List<Article>)
}