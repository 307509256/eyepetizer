package com.hareru.eyepetizer.remote

import com.hareru.eyepetizer.AppContext
import com.hareru.eyepetizer.remote.interceptor.CacheInterceptor
import com.hareru.eyepetizer.remote.interceptor.PublicParametersInterceptor
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Cache
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import okhttp3.CacheControl
import java.util.concurrent.TimeUnit


val json = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = true
    coerceInputValues = true
}

private val contentType = "application/json".toMediaType()

private val parametersInterceptor = PublicParametersInterceptor()
private val loggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT).apply {
    setLevel(HttpLoggingInterceptor.Level.BASIC)
}
private val cacheInterceptor = CacheInterceptor()
private val client = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS) //连接超时阈值
        .writeTimeout(10, TimeUnit.SECONDS) //写超时阈值
        .readTimeout(10, TimeUnit.SECONDS)  //读超时阈值
        .retryOnConnectionFailure(true) //当失败后重试
        .addInterceptor(parametersInterceptor)
        .addInterceptor(loggingInterceptor)
        .addNetworkInterceptor(cacheInterceptor)
        .cache(Cache(AppContext.cacheDir, 50L * 1024L * 1024L))
        .build()

private var retrofit = Retrofit.Builder()
        .baseUrl("http://baobab.kaiyanapp.com")
        .client(client)
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()

var kaiYanService: KaiYanService = retrofit.create(KaiYanService::class.java)