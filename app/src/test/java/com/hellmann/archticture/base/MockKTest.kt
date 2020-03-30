package com.hellmann.archticture.base

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hellmann.archticture.base.rules.CoroutineTestRule
import com.hellmann.archticture.base.rules.MockKTestRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.koin.test.AutoCloseKoinTest

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 30/03/2020
 * 
 * (c) 2020 
 */
@ExperimentalCoroutinesApi
abstract class MockKTest : AutoCloseKoinTest() {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @get:Rule
    val mockKTestRule = MockKTestRule(this)
}
