package com.hareru.eyepetizer.ui.splash

import androidx.appcompat.app.AppCompatActivity
import coil.Coil
import coil.ImageLoader
import coil.util.CoilUtils
import com.hareru.eyepetizer.R
import com.hareru.eyepetizer.ui.MainActivity
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.MaterialHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.shuyu.gsyvideoplayer.player.IjkPlayerManager
import okhttp3.OkHttpClient
import tv.danmaku.ijk.media.player.IjkMediaPlayer

class SplashActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()

        IjkPlayerManager.setLogLevel(IjkMediaPlayer.IJK_LOG_SILENT)

        val imageLoader = ImageLoader.Builder(this)
                .crossfade(true)
                .placeholder(R.drawable.placeholder)
                .error(R.drawable.error)
                .availableMemoryPercentage(0.1)
                .bitmapPoolPercentage(0.1)
                //开启coil日志
                //.logger(DebugLogger())
                .okHttpClient {
                    OkHttpClient.Builder()
                            .cache(CoilUtils.createDefaultCache(this))
                            .build()
                }
                .build()
        Coil.setImageLoader(imageLoader)

        SmartRefreshLayout.setDefaultRefreshInitializer { _, layout ->
            layout.setEnableLoadMore(true)
            layout.setEnableLoadMoreWhenContentNotFull(true)
        }

        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            layout.setEnableHeaderTranslationContent(true)
            MaterialHeader(context).setColorSchemeResources(
                    android.R.color.holo_red_light,
                    android.R.color.holo_green_light,
                    android.R.color.holo_blue_light,
            )
        }

        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
            ClassicsFooter(context).setDrawableSize(20f)
        }

        MainActivity.start(this)

        finish()
    }
}