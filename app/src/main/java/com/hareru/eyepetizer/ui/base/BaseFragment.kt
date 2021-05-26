package com.hareru.eyepetizer.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import androidx.viewbinding.ViewBinding

/**
 * Fragment的ViewBinding视图绑定抽象类
 * @see <a href="https://developer.android.com/topic/libraries/view-binding">View Binding Part of Android Jetpack.</a>
 */
abstract class BaseFragment<VB : ViewBinding>(private val bindingInflater: (inflater: LayoutInflater, parent: ViewGroup?, attachToParent: Boolean) -> VB) : Fragment() {

    // 因为 fragment 的生命周期与 activity 的生命周期不同，并且该fragment 可以超出其视图的生命周期，因此如果不将其设置为null，则可能会发生内存泄漏。
    private var _binding: VB? = null
    protected val binding: VB
        get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(requireContext()).inflateTransition(android.R.transition.move);
    }

    final override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        if (_binding == null) _binding = bindingInflater.invoke(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    open fun initView() {}

    open fun initData() {}

    //表示任何覆盖方法也应调用此方法。
    @CallSuper
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}