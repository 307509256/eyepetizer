package com.hareru.eyepetizer.remote.interceptor

import android.os.Build
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*

class PublicParametersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val originalHttpUrl = originalRequest.url
        val newHttpUrl = originalHttpUrl.newBuilder()
                .host("baobab.kaiyanapp.com")
                .port(80)
                .addQueryParameter("vc", "6030012")
                .addQueryParameter("vn", "6.3.01")
                .addQueryParameter("size", "1920x1080")
                .addQueryParameter("udid", "49e812bb979cbf4f")
                .addQueryParameter("deviceModel", Build.MODEL)
                .addQueryParameter("first_channel", Build.BRAND)
                .addQueryParameter("last_channel", Build.BRAND)
                .addQueryParameter("system_version_code", "${Build.VERSION.SDK_INT}")
                .build()
        val newRequest = originalRequest.newBuilder()
                .url(newHttpUrl)
                .method(originalRequest.method, originalRequest.body)
                .addHeader("model", "Android")
//                .addHeader("If-Modified-Since", "${Date()}")
                .addHeader("User-Agent", System.getProperty("http.agent") ?: "unknown")
                .build()
        return chain.proceed(newRequest)
    }
}
