package io.miyabi.image.compare.layout

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.widget.FrameLayout
import android.widget.ImageView
import io.miyabi.image.compare.R

class ImageFrameLayout : FrameLayout, ScaleGestureDetector.OnScaleGestureListener {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    private enum class Mode {
        NONE,
        DRAG,
        ZOOM
    }

    private val MIN_ZOOM = 1.0f;
    private val MAX_ZOOM = 4.0f;

    private var mode : Mode = Mode.NONE
    private var scale = 1.0f;
    private var lastScaleFactor = 0f

    private var startX = 0.0f
    private var startY = 0.0f

    private var dx = 0f;
    private var dy = 0f;
    private var prevDx = 0f;
    private var prevDy = 0f;

    var changed = false

    init {
        val detector = ScaleGestureDetector(context, this)
        createScaleDetector(detector)
    }

    private fun createScaleDetector(detector: ScaleGestureDetector) {
        setOnTouchListener { v, event ->
            proceedMotionEvent(event)
            detector.onTouchEvent(event)
            if (Mode.DRAG == mode && scale >= MIN_ZOOM || Mode.ZOOM == mode)
                scaleViews()
            true
        }
    }

    private fun proceedMotionEvent(event: MotionEvent) {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                mode = Mode.DRAG
                startX = event.x - prevDx
                startY = event.y - prevDy
            }
            MotionEvent.ACTION_MOVE -> {
                if (Mode.DRAG == mode) {
                    dx = event.x - startX
                    dy = event.y - startY
                }
            }
            MotionEvent.ACTION_POINTER_DOWN -> {
                mode = Mode.ZOOM
            }
            MotionEvent.ACTION_POINTER_UP -> {
                mode = Mode.DRAG
            }
            MotionEvent.ACTION_UP -> {
                mode = Mode.NONE
                prevDx = dx
                prevDy = dy
            }
        }
    }

    private fun scaleViews() {
        parent.requestDisallowInterceptTouchEvent(true)
        val child = getChildAt(0)
        val maxDx = (child.width - (child.width / scale)) / 2 * scale
        val maxDy = (child.height - (child.height / scale)) / 2 * scale
        dx = Math.min(Math.max(dx, -maxDx), maxDx)
        dy = Math.min(Math.max(dy, -maxDy), maxDy)
        child.scaleX = scale
        child.scaleY = scale
        child.translationX = dx
        child.translationY = dy
        val beforeView = findViewById<ImageView>(R.id.before_image)
        val afterView = findViewById<ImageView>(R.id.after_image)
        beforeView?.scaleX = scale
        beforeView?.scaleY = scale
        beforeView?.translationX = dx
        beforeView?.translationY = dy
        afterView?.scaleX = scale
        afterView?.scaleY = scale
        afterView?.translationX = dx
        afterView?.translationY = dy
    }

    override fun onScaleBegin(detector: ScaleGestureDetector?): Boolean {
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector?) {
    }

    override fun onScale(detector: ScaleGestureDetector?): Boolean {
        detector?.also {
            var factor = 0f
            if (lastScaleFactor == 0f || (Math.signum(it.scaleFactor) == Math.signum(lastScaleFactor))) {
                scale *= it.scaleFactor
                scale = Math.max(MIN_ZOOM, Math.min(scale, MAX_ZOOM))
                factor = it.scaleFactor
            }
            lastScaleFactor = factor
        } ?: run {
            lastScaleFactor = 0f
        }
        return true
    }

}