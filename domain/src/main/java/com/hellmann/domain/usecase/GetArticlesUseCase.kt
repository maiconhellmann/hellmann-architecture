package com.hellmann.domain.usecase

import com.hellmann.domain.entity.Article
import com.hellmann.domain.repository.ArticleReposity
import io.reactivex.Observable
import io.reactivex.Scheduler

class GetArticlesUseCase(
    private val repository: ArticleReposity,
    private val scheduler: Scheduler
) {

    fun execute(forceUpdate: Boolean): Observable<List<Article>> {
        return repository.getArticles().map { it.articles }.subscribeOn(scheduler)
    }
}