package com.hellmann.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.hellmann.data.local.model.ArticleCache

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 25/05/2019
 * 
 * (c) 2019
 *
 */
@Database(version = 1, entities = [ArticleCache::class])
abstract class ArticleDataBase : RoomDatabase() {
    abstract fun articleDao(): ArticleDao

    companion object {
        fun createDatabase(context: Context): ArticleDao {
            return Room
                .databaseBuilder(context, ArticleDataBase::class.java, "Articles.db")
                .fallbackToDestructiveMigration()
                .build()
                .articleDao()
        }
    }
}