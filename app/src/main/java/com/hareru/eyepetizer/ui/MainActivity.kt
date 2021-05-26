package com.hareru.eyepetizer.ui

import android.content.Context
import android.content.Intent
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.hareru.eyepetizer.ui.base.BaseActivity
import com.hareru.eyepetizer.R
import com.hareru.eyepetizer.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    private val TAG = "MainActivity"
    override fun initView() {
    }

    override fun onStart() {
        super.onStart()
        // 使用 FragmentContainerView 创建 NavHostFragment，或通过 FragmentTransaction 手动将 NavHostFragment 添加到您的 Activity 时，
        // 尝试通过 Navigation.findNavController(Activity, @IdRes int) 检索 Activity 的 onCreate() 中的 NavController 将失败。
        val navController = binding.fragmentContainerView.findNavController()
        binding.bottomNav.setupWithNavController(navController)
        binding.bottomNav.setOnNavigationItemReselectedListener(null)
        binding.bottomNav.setOnNavigationItemSelectedListener {
            if (it.isChecked || navController.popBackStack(it.itemId, false)) {
                true
            } else {
                NavigationUI.onNavDestinationSelected(it, navController)
            }
        }
    }

    companion object {
        @JvmStatic
        fun start(context: Context) {
            val starter = Intent(context, MainActivity::class.java)
            context.startActivity(starter)
        }
    }

    //需要重写onSupportNavigateUp方法
    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.fragmentContainerView).navigateUp()
    }

}