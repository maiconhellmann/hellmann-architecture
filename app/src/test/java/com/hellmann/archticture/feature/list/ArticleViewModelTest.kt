package com.hellmann.archticture.feature.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.hellmann.archticture.feature.viewmodel.ViewState
import com.hellmann.archticture.util.CoroutineTestRule
import com.hellmann.domain.entity.Article
import com.hellmann.domain.usecase.GetArticlesUseCase
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import io.mockk.verifyAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.test.AutoCloseKoinTest

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 10/06/2019
 * 
 * (c) 2019 
 */
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ArticleViewModelTest : AutoCloseKoinTest() {

    lateinit var viewModel: ArticleViewModel

    @MockK
    lateinit var useCase: GetArticlesUseCase

    @MockK(relaxed = true)
    lateinit var viewStateObserver: Observer<ViewState<List<Article>>>

    /**
     * Changes the main thread to be able to tests
     */
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    /**
     * Prevents the following exception:
     * Exception in thread main @coroutine#2 java.lang.RuntimeException: Method getMainLooper in android.os.Looper not mocked.
     */
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `viewModel - default`() = runBlockingTest {
        // Given
        val list = listOf(Article("Title"))
        coEvery { useCase.execute(false) } returns list

        // When
        viewModel = ArticleViewModel(useCase, coroutineTestRule.dispatcher)
        viewModel.state.observeForever(viewStateObserver)

        // Then
        verifyAll {
            // Verifies all states emitted
            viewStateObserver.onChanged(ViewState.Loading)
            viewStateObserver.onChanged(ViewState.Success(list))
        }
    }

    @Test
    fun `viewModel - refresh`() = runBlockingTest {
        // Given
        coEvery { useCase.execute(false) } returns emptyList()

        // When
        viewModel = ArticleViewModel(useCase, coroutineTestRule.dispatcher)
        viewModel.state.observeForever(viewStateObserver)

        // Then
        verify {
            viewStateObserver.onChanged(ViewState.Loading)
            viewStateObserver.onChanged(ViewState.Success(emptyList()))
        }

        // Given
        val list = listOf(Article("Title"))
        coEvery { useCase.execute(true) } returns list

        // When
        viewModel.onTryAgainRequired()

        // Then
        verify {
            viewStateObserver.onChanged(ViewState.Loading)
            viewStateObserver.onChanged(ViewState.Success(list))
        }
    }
}
