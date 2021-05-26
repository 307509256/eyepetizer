package com.hareru.eyepetizer.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hareru.eyepetizer.databinding.FragmentHomeBinding
import com.hareru.eyepetizer.remote.KaiYanService
import com.hareru.eyepetizer.ui.search.SearchActivity
import com.hareru.eyepetizer.ui.base.BaseFragment
import com.hareru.eyepetizer.ui.refresh.RefreshListFragment

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::inflate) {
    override fun initView() {
        val titles = listOf("发现", "推荐", "日报")
        val initialKeys = listOf(KaiYanService.DISCOVERY_URL, KaiYanService.RECOMMEND_URL, KaiYanService.DAILY_URL)
        binding.vp.adapter = object : FragmentStateAdapter(childFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return initialKeys.size
            }

            override fun createFragment(position: Int): Fragment {
                return RefreshListFragment.newInstance(initialKeys[position])
            }
        }
        binding.vp.setCurrentItem(1, false)
        TabLayoutMediator(binding.tab, binding.vp, false, true) { tab: TabLayout.Tab, i: Int ->
            tab.text = titles[i]
        }.attach()
        binding.search.setOnClickListener {
            SearchActivity.start(requireContext())
        }
    }
}