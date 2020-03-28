package com.hellmann.data.remote

import com.hellmann.data.remote.api.ServerApi
import com.hellmann.data.remote.model.ArticlePayload
import com.hellmann.data.remote.model.ArticlesPayload
import com.hellmann.data.remote.source.RemoteDataSource
import com.hellmann.data.remote.source.RemoteDataSourceImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see [Testing documentation](http://d.android.com/tools/testing)
 */
class RemoteDataSourceTest {

    private lateinit var remoteDataSource: RemoteDataSource

    @MockK
    private lateinit var serverApi: ServerApi

    @Before
    fun prepare() {
        MockKAnnotations.init(this)
        remoteDataSource = RemoteDataSourceImpl(serverApi)
    }

    @Test
    fun `article list is empty`() = runBlockingTest {
        coEvery { serverApi.fetchArticles() } returns ArticlesPayload("ok", 0, emptyList())

        with(remoteDataSource.getArticles()) {
            assert(isEmpty())
        }
    }

    @Test
    fun `article list not empty`() = runBlockingTest {
        coEvery {
            serverApi.fetchArticles()
        } returns ArticlesPayload("ok", 0, listOf(ArticlePayload("", "")))

        with(remoteDataSource.getArticles()) {
            assert(isNotEmpty())
            assert(size == 1)
        }
    }
}
