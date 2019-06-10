package com.hellmann.archticture.feature.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hellmann.archticture.di.presentationModuleTest
import com.hellmann.archticture.feature.viewmodel.ViewState
import com.hellmann.domain.entity.Article
import com.hellmann.domain.usecase.GetArticlesUseCase
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.koin.core.context.startKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.inject
import org.mockito.Mockito
import org.mockito.Mockito.mock

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 10/06/2019
 * 
 * (c) 2019 
 */
class ArticleViewModelTest : AutoCloseKoinTest() {

    val viewModel: ArticleViewModel by inject()
    val useCase: GetArticlesUseCase by inject()

    //A JUnit Test Rule that swaps the background executor used by the Architecture Components with a different one which executes each task synchronously.
    //https://developer.android.com/reference/android/arch/core/executor/testing/InstantTaskExecutorRule
    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @Before
    fun before() {
        val mockUseCase = mock(GetArticlesUseCase::class.java)
        val module = module { factory { mockUseCase } }

        //Needs to be mocked before injection(maybe try using mock by koin
        Mockito.`when`(mockUseCase.execute(true)).then {
            Single.just(listOf(Article("Title")))
        }

        startKoin {
            modules(presentationModuleTest + module)
        }
    }

    @Test
    fun viewModelTest() {

        assert(viewModel.state.value == ViewState.Loading)

        viewModel.getJobs(true)

        assert(viewModel.state.value is ViewState.Success)

        with(viewModel.state.value as ViewState.Success) {
            assert(data.isNotEmpty())
            assert(data.first().title == "Title")
        }
    }
}