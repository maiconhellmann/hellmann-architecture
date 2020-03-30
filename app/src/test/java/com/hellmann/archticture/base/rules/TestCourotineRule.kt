package com.hellmann.archticture.base.rules

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 29/03/2020
 * 
 * (c) 2020 
 */
@ExperimentalCoroutinesApi
class CoroutineTestRule(
    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher(),
    private val testCoroutineScope: TestCoroutineScope = TestCoroutineScope(dispatcher)
) : TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        dispatcher.cleanupTestCoroutines()
    }

    fun runBlockingTest(block: suspend TestCoroutineScope.() -> Unit) =
        testCoroutineScope.runBlockingTest { block() }
}
