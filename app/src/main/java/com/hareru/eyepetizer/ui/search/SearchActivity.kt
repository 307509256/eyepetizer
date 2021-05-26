package com.hareru.eyepetizer.ui.search

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View.*
import android.view.WindowInsets
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hareru.eyepetizer.databinding.ActivitySearchBinding
import com.hareru.eyepetizer.ui.base.BaseActivity
import com.hareru.eyepetizer.ui.refresh.RefreshListAdapter
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class SearchActivity : BaseActivity<ActivitySearchBinding>(ActivitySearchBinding::inflate) {
    private val viewModel by viewModels<SearchViewModel>()
    private val refreshListAdapter = RefreshListAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        /**
         * 假如你想要通过setTheme设置透明主题样式的话，android系统似乎自动忽略了你透明主题中的windowIsTranslucent=true这个标志，依然还是显示非透明的主题样式
         */
        super.onCreate(savedInstanceState)
        val enterAnimation = TranslateAnimation(1, 0f, 1, 0f, 1, -1f, 1, 0f)
        enterAnimation.duration = 500
        binding.root.setLayerType(LAYER_TYPE_HARDWARE, null)
        binding.root.startAnimation(enterAnimation)
        showSoftInput(enterAnimation.duration)
    }

    override fun initView() {
        tStatusBars()
        binding.etQuery.setOnEditorActionListener { view, editorInfo, _ ->
            if (editorInfo == EditorInfo.IME_ACTION_SEARCH) {
                onSearch(view.text.toString())
            }
            return@setOnEditorActionListener true
        }
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = refreshListAdapter
    }

    private fun tStatusBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            //配合Android 11 IME 动画使用
            window.setDecorFitsSystemWindows(false)
            binding.root.setOnApplyWindowInsetsListener { _, insets ->
                val statusBars = insets.getInsets(WindowInsets.Type.statusBars())
                binding.til.setPadding(0, statusBars.top, 0, 0)
                return@setOnApplyWindowInsetsListener insets
            }
        }
    }

    private fun onSearch(key: String) {
        viewModel.setKey(key)
        hideSoftInput()
    }

    override fun initData() {
        viewModel.data.observe(this) {
            refreshListAdapter.submitData(lifecycle, it)
        }
    }


    private fun showSoftInput(timeMillis: Long = 0) {
        lifecycleScope.launch {
            delay(timeMillis)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                binding.etQuery.windowInsetsController?.show(WindowInsets.Type.ime())
                binding.etQuery.requestFocus()
            } else {
                binding.etQuery.requestFocus()
                val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputManager.showSoftInput(binding.etQuery, InputMethodManager.SHOW_FORCED)
            }
        }
    }

    fun hideSoftInput() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            binding.etQuery.windowInsetsController?.hide(WindowInsets.Type.ime())
            binding.etQuery.clearFocus()
        } else {
            val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(binding.etQuery.windowToken, InputMethodManager.HIDE_IMPLICIT_ONLY)
            binding.etQuery.clearFocus()
        }
    }

    override fun finish() {
        val exitAnimation = TranslateAnimation(1, 0f, 1, 0f, 1, 0f, 1, -1f)
        exitAnimation.duration = 500
        exitAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                hideSoftInput()
            }

            override fun onAnimationEnd(animation: Animation?) {
                binding.root.setLayerType(LAYER_TYPE_NONE, null)
                superFinish()
                overridePendingTransition(0, 0)
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        binding.root.visibility = INVISIBLE
        binding.root.setLayerType(LAYER_TYPE_HARDWARE, null)
        binding.root.startAnimation(exitAnimation)
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, SearchActivity::class.java)
            context.startActivity(starter)
        }
    }
}