package com.hellmann.archticture.feature.list

import androidx.lifecycle.Observer
import com.hellmann.archticture.base.MockKTest
import com.hellmann.archticture.feature.viewmodel.ViewState
import com.hellmann.domain.entity.Article
import com.hellmann.domain.usecase.GetArticlesUseCase
import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 10/06/2019
 * 
 * (c) 2019 
 */
@ExperimentalCoroutinesApi
@RunWith(JUnit4::class)
class ArticleViewModelTest : MockKTest() {

    lateinit var viewModel: ArticleViewModel

    @MockK
    lateinit var useCase: GetArticlesUseCase

    lateinit var viewStateObserver: Observer<ViewState<List<Article>>>

    @Test
    fun `viewModel - default`() = runBlockingTest {
        // Given
        val list = listOf(Article("Title"))
        coEvery { useCase.execute(false) } returns list

        // When
        viewModel = ArticleViewModel(useCase)

        viewStateObserver = spyk(Observer {})
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
        viewModel = ArticleViewModel(useCase)

        viewStateObserver = spyk(Observer {})
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

    @After
    fun tearDown() {
        confirmVerified(viewStateObserver)
    }
}
