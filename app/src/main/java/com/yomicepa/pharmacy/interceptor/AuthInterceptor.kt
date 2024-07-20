package com.yomicepa.pharmacy.interceptor

import okhttp3.Interceptor
import okhttp3.Response

// we create this in order to the token in all api requests after receive it from the login
class AuthInterceptor(private var authToken: String? = null) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        authToken?.let {
            requestBuilder.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(requestBuilder.build())
    }

    fun setToken(token: String) {
        authToken = token
    }
}
