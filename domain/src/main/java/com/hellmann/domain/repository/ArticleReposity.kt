package com.hellmann.domain.repository

import com.hellmann.domain.entity.ArticlesDto
import io.reactivex.Observable

interface ArticleReposity {
    fun getArticles(): Observable<ArticlesDto>
}