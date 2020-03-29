package com.hellmann.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import com.hellmann.data.di.cacheDataModuleTest
import com.hellmann.data.local.database.ArticleDao
import com.hellmann.data.local.model.ArticleCache
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.core.inject
import org.koin.test.AutoCloseKoinTest
import org.koin.test.KoinTestRule

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 09/06/2019
 * 
 * (c) 2019 
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4ClassRunner::class)
class ArticleDaoTest : AutoCloseKoinTest() {

    /**
     * Starts a koin appplication for this test
     */
    @get:Rule
    val koinTestRule = KoinTestRule.create {
        androidContext(InstrumentationRegistry.getInstrumentation().context)
        modules(cacheDataModuleTest)
    }

    /**
     * Used to solve the issue: runBlockingTest fails with "This job has not completed yet"
     * https://github.com/Kotlin/kotlinx.coroutines/issues/1204
     */
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    val articleDao: ArticleDao by inject()

    @Test
    fun articleDaoTesting() = runBlockingTest {
        articleDao.insertAll(listOf(ArticleCache(0L, "title", "desc", "url", "urlToImage")))

        val responseList = articleDao.getAll()
        assert(responseList.isNotEmpty())
        assert(responseList.first().title == "title")
        assert(responseList.first().url == "url")
    }
}
