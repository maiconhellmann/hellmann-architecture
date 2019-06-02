package com.hellmann.data.remote.model

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 25/05/2019
 * 
 * (c) 2019 
 */
class ArticlesPayload(
    val status: String, val totalResults: Long, val articles: List<ArticlePayload> = emptyList()
)

class ArticlePayload(
    val title: String,
    val description: String,
    val url: String? = null,
    val urlToImage: String? = null,
    val publishedAt: String? = null
)