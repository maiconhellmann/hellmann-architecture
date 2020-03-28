package com.hellmann.data.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.hellmann.data.local.model.ArticleCache

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 25/05/2019
 * Data Access object
 *
 * (c) 2019
 */
@Dao
interface ArticleDao {
    @Query("SELECT * FROM ArticleCache")
    suspend fun getAll(): List<ArticleCache>

    @Query("DELETE FROM ArticleCache")
    suspend fun deleteAll()

    @Insert
    fun insertAll(list: List<ArticleCache>)

    @Transaction
    suspend fun updateDate(list: List<ArticleCache>) {
        deleteAll()
        insertAll(list)
    }
}
