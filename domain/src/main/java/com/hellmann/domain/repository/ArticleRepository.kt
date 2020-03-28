package com.hellmann.domain.repository

import com.hellmann.domain.entity.Article

interface ArticleRepository {
    suspend fun getArticles(forceUpdate: Boolean): List<Article>
}
