package com.hareru.eyepetizer.ui.refresh

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hareru.eyepetizer.R
import com.hareru.eyepetizer.databinding.FragmentRefreshLayoutBinding
import com.hareru.eyepetizer.ui.base.BaseFragment
import com.hareru.eyepetizer.view.delegate.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.shuyu.gsyvideoplayer.utils.CommonUtil


class RefreshListFragment : BaseFragment<FragmentRefreshLayoutBinding>(FragmentRefreshLayoutBinding::inflate) {
    private val viewModel by viewModels<RefreshListViewModel>()
    private val refreshListAdapter by lazy { RefreshListAdapter() }


    override fun initView() {
        //限定范围为屏幕一半的上下偏移180
        val playTop = CommonUtil.getScreenHeight(requireContext()) / 2 - CommonUtil.dip2px(requireContext(), 180f)
        val playBottom = CommonUtil.getScreenHeight(requireContext()) / 2 + CommonUtil.dip2px(requireContext(), 180f)
        val scrollCalculatorHelper = ScrollCalculatorHelper(R.id.videoPlayer, playTop, playBottom);
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.rv.layoutManager = linearLayoutManager
        binding.rv.adapter = refreshListAdapter
        binding.rv.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            var firstVisibleItem = 0
            var lastVisibleItem: Int = 0
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                scrollCalculatorHelper.onScrollStateChanged(recyclerView, newState);
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                firstVisibleItem = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();

                //这是滑动自动播放的代码
                scrollCalculatorHelper.onScroll(recyclerView, firstVisibleItem, lastVisibleItem, lastVisibleItem - firstVisibleItem);

            }
        })
        binding.srl.setOnRefreshListener {
            lifecycleScope.launch {
                delay(1000)
                viewModel.refresh()
            }
        }
    }

    override fun initData() {
        val initialKey = arguments?.getString(InitialKey)
        if (initialKey.isNullOrBlank()) return
        viewModel.setInitialKey(initialKey)
        refreshListAdapter.addDelegate(HorizontalScrollCardDelegate(), SpecialSquareCardCollectionDelegate(), ColumnCardListDelegate())
        viewModel.data.observe(viewLifecycleOwner) {
            binding.srl.isRefreshing = false
            refreshListAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        }
    }

    companion object {
        const val InitialKey = "initialKey"
        fun newInstance(initialKey: String): RefreshListFragment {
            val args = Bundle()
            args.putString(InitialKey, initialKey)
            val fragment = RefreshListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}