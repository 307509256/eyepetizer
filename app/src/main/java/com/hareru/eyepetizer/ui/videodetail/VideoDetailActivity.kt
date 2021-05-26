package com.hareru.eyepetizer.ui.videodetail

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.hareru.eyepetizer.bean.VideoBeanForClient
import com.hareru.eyepetizer.databinding.ActivityNewDetailBinding
import com.hareru.eyepetizer.ui.base.BaseActivity
import com.hareru.eyepetizer.ui.refresh.RefreshListAdapter
import com.shuyu.gsyvideoplayer.GSYVideoADManager
import com.shuyu.gsyvideoplayer.GSYVideoManager
import com.shuyu.gsyvideoplayer.utils.OrientationUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VideoDetailActivity : BaseActivity<ActivityNewDetailBinding>(ActivityNewDetailBinding::inflate) {
    private val viewModel by viewModels<VideoDetailViewModel>()
    private val videoInfoAdapter = RefreshListAdapter()
    private val relatedAdapter = RefreshListAdapter()
    private val replyAdapter = RefreshListAdapter()

    private val config = ConcatAdapter.Config.Builder()
            .setStableIdMode(ConcatAdapter.Config.StableIdMode.NO_STABLE_IDS)
            /**
             * 如果为false，则ConcatAdapter假定所有分配的适配器共享一个全局视图类型池，
             * 以便它们使用相同的视图类型引用相同的RecyclerView.ViewHolders。
             * 将此设置为false将允许嵌套适配器共享RecyclerView。
             * 但它也意味着这些适配器不应该有冲突的视图类型(RecyclerView.Adapter.getItemViewType(int))，
             * 这样两个不同的适配器返回相同的视图类型不同的RecyclerView.ViewHolders。
             * 默认情况下，它被设置为true，这意味着ConcatAdapter将跨适配器隔离视图类型，防止它们使用相同的RecyclerView.ViewHolders。
             */
            .setIsolateViewTypes(false)
            .build()
    private val concatAdapter = ConcatAdapter(config, videoInfoAdapter, relatedAdapter, replyAdapter)

    //屏幕旋转处理
    private val orientationUtils by lazy { OrientationUtils(this, binding.videoPlayer) }

    private fun hideStatusBars(timeMillis: Long = 0) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            lifecycleScope.launch {
                delay(timeMillis)
                window.insetsController?.hide(WindowInsets.Type.statusBars())
            }
        }
    }

    private fun showStatusBars(timeMillis: Long = 0) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            lifecycleScope.launch {
                delay(timeMillis)
                window.insetsController?.show(WindowInsets.Type.statusBars())
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val enterAnimation = TranslateAnimation(1, 0f, 1, 0f, 1, 1f, 1, 0f)
        enterAnimation.duration = 500
        binding.root.setLayerType(View.SCROLLBAR_POSITION_RIGHT, null)
        binding.root.startAnimation(enterAnimation)
        hideStatusBars(enterAnimation.duration - 200)
    }

    override fun initView() {
        binding.recyclerView.adapter = concatAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun initData() {
        val videoBean = intent.getParcelableExtra<VideoBeanForClient>("videoBeanForClient")!!
        startVideoPlayer(videoBean)
        viewModel.setVideoInfo(videoBean)
        viewModel.videoInfo.observe(this) {
            videoInfoAdapter.submitData(lifecycle, it)
        }
        viewModel.related.observe(this) {
            relatedAdapter.submitData(lifecycle, it)
        }
        viewModel.replies.observe(this) {
            replyAdapter.submitData(lifecycle, it)
        }
    }


    private fun startVideoPlayer(videoBean: VideoBeanForClient) {
        binding.videoPlayer.apply {
            fullscreenButton.setOnClickListener {
                orientationUtils.resolveByClick()
                binding.videoPlayer.startWindowFullscreen(context, true, false)
            }
            backButton.setOnClickListener {
                finish()
            }
            thumbImageView = ImageView(context).apply {
                scaleType = ImageView.ScaleType.CENTER_CROP
                load(videoBean.cover.detail)
            }
            playTag = "${videoBean.id}"
            isReleaseWhenLossAudio = false
            isRotateViewAuto = false
            isNeedLockFull = true
            dismissControlTime = 5000
            setIsTouchWiget(true)
            setUp(videoBean.playUrl, true, videoBean.title)
            startPlayLogic()
        }
    }

    override fun onBackPressed() {
        orientationUtils.backToProtVideo()
        if (GSYVideoManager.backFromWindowFull(this)) return
        super.onBackPressed()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        binding.videoPlayer.onConfigurationChanged(this, newConfig, orientationUtils, false, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        GSYVideoADManager.releaseAllVideos()
        orientationUtils.releaseListener()
        binding.videoPlayer.release()
        binding.videoPlayer.setVideoAllCallBack(null)
    }

    override fun onPause() {
        super.onPause()
        binding.videoPlayer.onVideoPause()
    }

    override fun onResume() {
        super.onResume()
        binding.videoPlayer.onVideoResume()
    }

    private fun onAnimationEnd() {
        binding.root.setLayerType(View.LAYER_TYPE_NONE, null);
        super.finish()
        overridePendingTransition(0, 0)
    }

    override fun finish() {
        val exitAnimation = TranslateAnimation(1, 0f, 1, 0f, 1, 0f, 1, 1f)
        exitAnimation.duration = 500
        exitAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                showStatusBars()
            }

            override fun onAnimationEnd(animation: Animation?) {
                onAnimationEnd()
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        binding.root.visibility = View.INVISIBLE
        binding.root.setLayerType(View.SCROLLBAR_POSITION_RIGHT, null)
        binding.root.startAnimation(exitAnimation)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        val videoBean = intent.getParcelableExtra<VideoBeanForClient>("videoBeanForClient")!!
        startVideoPlayer(videoBean)
        viewModel.setVideoInfo(videoBean)
    }

    companion object {
        @JvmStatic
        fun start(context: Context, videoBeanForClient: VideoBeanForClient) {
            val starter = Intent(context, VideoDetailActivity::class.java)
                    .putExtra("videoBeanForClient", videoBeanForClient)
            context.startActivity(starter)
        }
    }
}


