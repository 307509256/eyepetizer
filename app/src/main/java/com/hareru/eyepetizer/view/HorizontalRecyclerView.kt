package com.hareru.eyepetizer.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.abs
import kotlin.math.roundToInt

/**
 * 处理嵌套ViewPager时，横向滑动冲突。
 */
class HorizontalRecyclerView : RecyclerView {

    private var lastX = 0f

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        parent.requestDisallowInterceptTouchEvent(canScrollHorizontally((lastX - ev.x).toInt()))
        lastX = ev.x
        return super.dispatchTouchEvent(ev)
    }

}
