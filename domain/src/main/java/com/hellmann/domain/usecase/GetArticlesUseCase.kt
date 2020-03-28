package com.hellmann.domain.usecase

import com.hellmann.domain.entity.Article
import com.hellmann.domain.repository.ArticleRepository

class GetArticlesUseCase(
    private val repository: ArticleRepository
) {
    suspend fun execute(forceUpdate: Boolean): List<Article> {
        return repository.getArticles(forceUpdate)
    }
}
