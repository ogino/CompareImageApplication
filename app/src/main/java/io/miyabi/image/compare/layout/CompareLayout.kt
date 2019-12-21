package io.miyabi.image.compare.layout

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.SeekBar
import android.widget.TextView
import io.miyabi.image.compare.R
import io.miyabi.image.compare.extension.loadImage
import io.miyabi.image.compare.extension.stayVisibleOrGone
import io.miyabi.image.compare.task.ClipDrawableTask
import org.apache.commons.lang3.exception.ExceptionUtils
import java.util.logging.Logger

class CompareLayout : RelativeLayout, ClipDrawableTask.AfterImage {

    private val logger = Logger.getLogger(javaClass.canonicalName.orEmpty())
    private var beforeImageView : ImageView
    private var afterImageView : ImageView
    private var seekBar : SeekBar
    private var beforeTextView : TextView
    private var afterTextView: TextView

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        val attr = context?.theme?.obtainStyledAttributes(attrs, R.styleable.CompareLayout,0,0)
        attr?.let {
            try {
                putThumb(it.getDrawable(R.styleable.CompareLayout_slider_thumb))
                putBefore(it.getDrawable(R.styleable.CompareLayout_before_image))
                putAfter(it.getDrawable(R.styleable.CompareLayout_after_image))
            } catch (t: Throwable) {
                logger.severe(ExceptionUtils.getStackTrace(t))
            } finally {
                it.recycle()
            }
        }
    }

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_compare, this)
        beforeImageView = findViewById(R.id.before_image)
        afterImageView = findViewById(R.id.after_image)
        seekBar = findViewById(R.id.seekbar)
        beforeTextView = findViewById(R.id.before_text)
        afterTextView = findViewById(R.id.after_text)
    }

    override fun onLoaded(success: Boolean) {
        seekBar.stayVisibleOrGone(success)
    }

    private val afterHide = 1000

    override fun textVisible(progress: Int) {
        afterTextView.visibility = if (afterHide < progress) View.VISIBLE else View.GONE
    }

    fun putBefore(imageUri: String): CompareLayout {
        beforeImageView.loadImage(imageUri)
        return this
    }

    fun putBefore(imgDrawable: Drawable?): CompareLayout {
        imgDrawable?.let {
            beforeImageView.loadImage(it)
        }
        return this
    }

    fun putAfter(imageUri: String) {
        if (imageUri.isNotBlank()) {
            val task = ClipDrawableTask<String>(afterImageView, seekBar, context, this)
            task.execute(imageUri)
        }
    }

    fun putAfter(imageDrawable: Drawable?) {
        imageDrawable?.let {
            val task = ClipDrawableTask<Drawable>(afterImageView, seekBar, context, this)
            task.execute(it)
        }
    }

    fun putThumb(thumb: Drawable?){
        thumb?.let {
            seekBar.thumb = thumb
        }
    }
}