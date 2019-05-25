package com.hellmann.domain.repository

import com.hellmann.domain.entity.Article
import io.reactivex.Single

interface ArticleRepository {
    fun getArticles(forceUpdate: Boolean): Single<List<Article>>
}