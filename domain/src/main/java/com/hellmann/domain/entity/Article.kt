package com.hellmann.domain.entity

data class Article(
    val title: String,
    val description: String,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null
)