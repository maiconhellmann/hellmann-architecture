package com.hellmann.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ArticleCache(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val title: String = "",
    val description: String? = "",
    val url: String? = "",
    val urlToImage: String? = "",
    val publishedAt: String? = ""
)