package com.hareru.eyepetizer.ui.base

import android.os.Bundle
import android.view.*
import androidx.annotation.CallSuper
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

/**
 * AppCompatActivity的ViewBinding视图绑定抽象类
 * @see <a href="https://developer.android.com/topic/libraries/view-binding">View Binding Part of Android Jetpack.</a>
 */
abstract class BaseActivity<VB : ViewBinding>(private val bindingInflater: (inflater: LayoutInflater) -> VB) : AppCompatActivity() {
    protected lateinit var binding: VB

    @CallSuper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = bindingInflater.invoke(layoutInflater)
        setContentView(binding.root)
        initView()
        initData()
    }

    open fun initView() {}

    open fun initData() {}

    protected fun superFinish() {
        super.finish()
    }
}