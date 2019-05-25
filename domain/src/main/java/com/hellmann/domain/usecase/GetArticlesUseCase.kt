package com.hellmann.domain.usecase

import com.hellmann.domain.entity.Article
import com.hellmann.domain.repository.ArticleRepository
import io.reactivex.Scheduler
import io.reactivex.Single

class GetArticlesUseCase(
    private val repository: ArticleRepository,
    private val scheduler: Scheduler
) {

    fun execute(forceUpdate: Boolean): Single<List<Article>> {
        return repository.getArticles(forceUpdate).subscribeOn(scheduler)
    }
}