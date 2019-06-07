package com.hellmann.data.remote

import com.hellmann.data.BuildConfig
import com.hellmann.data.remote.api.interceptor.AuthenticationRequestInterceptor
import okhttp3.Connection
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 05/06/2019
 * 
 * (c) 2019 
 */class AuthenticationRequestInterceptorTest {

    private lateinit var chain: Interceptor.Chain
    private lateinit var interceptor: AuthenticationRequestInterceptor

    @Before
    fun init() {
        chain = object : Interceptor.Chain {
            override fun proceed(request: Request): Response {
                val response = mock(Response::class.java)
                `when`(response.request()).then { request }
                return response
            }

            override fun connection(): Connection? {
                return null
            }

            override fun request(): Request {
                return Request.Builder().url("http://www.test.com").build()
            }
        }

        interceptor = AuthenticationRequestInterceptor()
    }

    @Test
    fun `add authentication header`() {
        val response = interceptor.intercept(chain)

        //assert that has authentication
        assert(response.request().header(AuthenticationRequestInterceptor.KEY_AUTHENTICATION) == BuildConfig.API_KEY)
    }
}