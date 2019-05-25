package com.hellmann.domain.entity

data class ArticlesDto(
    val status: String,
    val totalResults: Long,
    val articles: List<Article> = emptyList()
)