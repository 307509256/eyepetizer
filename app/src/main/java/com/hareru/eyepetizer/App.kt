package com.hareru.eyepetizer

import android.app.Application
import android.content.ContextWrapper

private lateinit var mApplication: Application

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        mApplication = this
    }
}

//ContextWrapper对Context上下文进行包装(装饰者模式)
object AppContext : ContextWrapper(mApplication)