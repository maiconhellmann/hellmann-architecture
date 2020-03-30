package com.hellmann.archticture.base.rules

import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.unmockkAll
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 30/03/2020
 * 
 * (c) 2020 
 */
class MockKTestRule(private val obj: Any) : TestWatcher() {
    override fun starting(description: Description?) {
        super.starting(description)
        MockKAnnotations.init(obj)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        clearAllMocks()
        unmockkAll()
    }
}
