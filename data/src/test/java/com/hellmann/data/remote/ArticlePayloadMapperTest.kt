package com.hellmann.data.remote

import com.hellmann.data.remote.mapper.ArticlePayloadMapper
import com.hellmann.data.remote.model.ArticlePayload
import com.hellmann.data.remote.model.ArticlesPayload
import org.junit.Test

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 05/06/2019
 * 
 * (c) 2019 
 */class ArticlePayloadMapperTest {
    @Test
    fun `ArticlePayload to Article`() {
        val payload = ArticlesPayload(
            "status", 200, listOf(
                ArticlePayload(
                    "title1", "description1", "url1", "urlToImage1", "publishedAt1")))

        val mapped = ArticlePayloadMapper.map(payload)

        assert(mapped.isEmpty().not())
        assert(mapped.size == payload.articles.size)
        assert(mapped[0].title == payload.articles[0].title)
        assert(mapped[0].description == payload.articles[0].description)
        assert(mapped[0].publishedAt == payload.articles[0].publishedAt)
        assert(mapped[0].url == payload.articles[0].url)
        assert(mapped[0].urlToImage == payload.articles[0].urlToImage)
    }
}