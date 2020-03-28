package com.hellmann.data.remote

import com.hellmann.data.BuildConfig
import com.hellmann.data.remote.api.interceptor.AuthenticationRequestInterceptor
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import okhttp3.*
import org.junit.Before
import org.junit.Test
import java.util.concurrent.TimeUnit

/*
 * This file is part of hellmann-architeture.
 * 
 * Created by maiconhellmann on 05/06/2019
 * 
 * (c) 2019 
 */
class AuthenticationRequestInterceptorTest {

    private lateinit var chain: Interceptor.Chain
    private lateinit var interceptor: AuthenticationRequestInterceptor

    @MockK
    private lateinit var response: Response

    @MockK
    private lateinit var request: Request

    @MockK
    private lateinit var call: Call

    @Before
    fun init() {
        MockKAnnotations.init(this)

        chain = object : Interceptor.Chain {
            override fun proceed(request: Request): Response {
                coEvery { response.request } returns request
                return response
            }

            override fun readTimeoutMillis() = 0

            override fun call(): Call {
                return call
            }

            override fun connectTimeoutMillis() = 0

            override fun connection(): Connection? {
                return null
            }

            override fun request(): Request {
                return Request.Builder().url("http://www.test.com").build()
            }

            override fun withConnectTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
                return this
            }

            override fun withReadTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
                return this
            }

            override fun withWriteTimeout(timeout: Int, unit: TimeUnit): Interceptor.Chain {
                return this
            }

            override fun writeTimeoutMillis() = 0
        }

        interceptor = AuthenticationRequestInterceptor()
    }

    @Test
    fun `add authentication header`() {
        val response = interceptor.intercept(chain)

        //assert that has authentication
        assert(response.request.header(AuthenticationRequestInterceptor.KEY_AUTHENTICATION) == BuildConfig.API_KEY)
    }
}
