package com.hareru.eyepetizer.ui.refresh

import android.content.Context
import android.util.AttributeSet
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer

class AutoPlayVideo2 : StandardGSYVideoPlayer {
    constructor(context: Context?, fullFlag: Boolean?) : super(context, fullFlag)
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun changeUiToNormal() {
        super.changeUiToNormal()
        hideAllWidget()
        setViewShowState(mStartButton, VISIBLE)
    }

    override fun changeUiToPreparingShow() {
        super.changeUiToPreparingShow()
        hideAllWidget()
    }

    override fun changeUiToPlayingShow() {
        super.changeUiToPlayingShow()
        hideAllWidget()
    }

    override fun showWifiDialog() {
        startPlayLogic();
    }

    override fun touchSurfaceMoveFullLogic(absDeltaX: Float, absDeltaY: Float) {
        super.touchSurfaceMoveFullLogic(absDeltaX, absDeltaY)
        mChangePosition = false;
        mChangeVolume = false;
        mBrightness = false;
    }

    override fun touchDoubleUp() {

    }

}