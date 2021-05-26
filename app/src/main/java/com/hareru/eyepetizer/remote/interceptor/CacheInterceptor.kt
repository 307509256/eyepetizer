package com.hareru.eyepetizer.remote.interceptor

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class CacheInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val response: Response = chain.proceed(request)
        return response.newBuilder()
                .header("Cache-Control", "public, max-age=300")
                .build()
    }
}