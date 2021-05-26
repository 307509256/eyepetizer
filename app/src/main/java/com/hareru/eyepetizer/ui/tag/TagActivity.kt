package com.hareru.eyepetizer.ui.tag

import android.graphics.Color
import android.os.Build
import android.view.WindowInsets
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import coil.load
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.hareru.eyepetizer.databinding.ActivityTagBinding
import com.hareru.eyepetizer.bean.TagIndex
import com.hareru.eyepetizer.ui.base.BaseActivity
import com.hareru.eyepetizer.ui.refresh.RefreshListFragment


class TagActivity : BaseActivity<ActivityTagBinding>(ActivityTagBinding::inflate) {
    private val viewModel by viewModels<TagViewModel>()


    override fun initView() {
        tStatusBars()
    }

    override fun initData() {
        val title = intent.data?.getQueryParameter("title")
        val tagId = intent.data?.pathSegments?.get(0)
        if (tagId.isNullOrBlank()) return
        viewModel.setTagID(tagId)
        viewModel.d1.observe(this) {
            initTagInfo(it.tagInfo)
            initTabInfo(it.tabInfo.tabList)
        }
    }

    private fun tStatusBars() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
            binding.root.setOnApplyWindowInsetsListener { _, insets ->
                val statusBars = insets.getInsets(WindowInsets.Type.statusBars())
                binding.toolbar.setPadding(0, statusBars.top, 0, 0)
                return@setOnApplyWindowInsetsListener insets
            }
        }
        window.statusBarColor = Color.TRANSPARENT
    }

    private fun initTagInfo(tagInfo: TagIndex.TagInfo) {
        binding.bgPicture.load(tagInfo.bgPicture)
        binding.name.text = tagInfo.name
        binding.description.text = "${tagInfo.description}\n${tagInfo.tagFollowCount} Follow | ${tagInfo.lookCount} look"
    }


    private fun initTabInfo(tabList: List<TagIndex.TabInfo.Tab>) {
        binding.vp.adapter = object : FragmentStateAdapter(supportFragmentManager, lifecycle) {
            override fun getItemCount(): Int {
                return tabList.size
            }

            override fun createFragment(position: Int): Fragment {
                return RefreshListFragment.newInstance(tabList[position].apiUrl)
            }
        }
        TabLayoutMediator(binding.tab, binding.vp, false, true) { tab: TabLayout.Tab, i: Int ->
            tab.text = tabList[i].name
        }.attach()
    }


}