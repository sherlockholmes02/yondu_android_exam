package com.davedecastro.yonduandroidexam.utils

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.OnScaleGestureListener
import android.view.View
import android.widget.LinearLayout

class ZoomableLinearLayout : LinearLayout, OnScaleGestureListener {
    private enum class Mode {
        NONE, DRAG, ZOOM
    }

    private var mode = Mode.NONE
    private var scale = 1.0f
    private var lastScaleFactor = 0f
    private var startX = 0f
    private var startY = 0f
    private var dx = 0f
    private var dy = 0f
    private var prevDx = 0f
    private var prevDy = 0f

    constructor(context: Context?) : super(context) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun init(context: Context?) {
        val scaleDetector = ScaleGestureDetector(context, this)
        setOnTouchListener { view, motionEvent ->
            when (motionEvent.action and MotionEvent.ACTION_MASK) {
                MotionEvent.ACTION_DOWN -> if (scale > MIN_ZOOM) {
                    mode = Mode.DRAG
                    startX = motionEvent.x - prevDx
                    startY = motionEvent.y - prevDy
                }
                MotionEvent.ACTION_MOVE -> if (mode == Mode.DRAG) {
                    dx = motionEvent.x - startX
                    dy = motionEvent.y - startY
                }
                MotionEvent.ACTION_POINTER_DOWN -> mode = Mode.ZOOM
                MotionEvent.ACTION_POINTER_UP -> mode = Mode.DRAG
                MotionEvent.ACTION_UP -> {
                    mode = Mode.NONE
                    prevDx = dx
                    prevDy = dy
                }
            }
            scaleDetector.onTouchEvent(motionEvent)
            if (mode == Mode.DRAG && scale >= MIN_ZOOM || mode == Mode.ZOOM) {
                parent.requestDisallowInterceptTouchEvent(true)
                val maxDx: Float =
                    (child().getWidth() - child().getWidth() / scale) / 2 * scale
                val maxDy: Float =
                    (child().getHeight() - child().getHeight() / scale) / 2 * scale
                dx = Math.min(Math.max(dx, -maxDx), maxDx)
                dy = Math.min(Math.max(dy, -maxDy), maxDy)
                applyScaleAndTranslation()
            }
            true
        }
    }

    override fun onScaleBegin(scaleDetector: ScaleGestureDetector): Boolean {
        return true
    }

    override fun onScale(scaleDetector: ScaleGestureDetector): Boolean {
        val scaleFactor = scaleDetector.scaleFactor
        if (lastScaleFactor == 0f || Math.signum(scaleFactor) == Math.signum(lastScaleFactor)) {
            scale *= scaleFactor
            scale = Math.max(MIN_ZOOM, Math.min(scale, MAX_ZOOM))
            lastScaleFactor = scaleFactor
        } else {
            lastScaleFactor = 0f
        }
        return true
    }

    override fun onScaleEnd(scaleDetector: ScaleGestureDetector) {}
    private fun applyScaleAndTranslation() {
        child().setScaleX(scale)
        child().setScaleY(scale)
        child().setTranslationX(dx)
        child().setTranslationY(dy)
    }

    private fun child(): View {
        return getChildAt(0)
    }

    companion object {
        private const val MIN_ZOOM = 1.0f
        private const val MAX_ZOOM = 4.0f
    }
}