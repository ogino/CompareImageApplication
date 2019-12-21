package io.miyabi.image.compare.extension

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.nostra13.universalimageloader.core.ImageLoader
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration
import org.apache.commons.lang3.exception.ExceptionUtils
import java.util.logging.Logger

fun ImageView.loadImage(imgUrl: String?) {
    val imageLoader = ImageLoader.getInstance()
    imageLoader.init(ImageLoaderConfiguration.createDefault(this.context))
    imageLoader.displayImage(imgUrl, this)
}

fun ImageView.loadImage(imgDrawable: Drawable?) {
    this.setImageDrawable(imgDrawable)
}

fun View.stayVisibleOrGone(stay: Boolean) {
    this.visibility = if (stay) View.VISIBLE else View.GONE
}

fun Bitmap.scaledBitmap() : Bitmap? {
    val logger = Logger.getLogger(Bitmap::class.java.canonicalName.orEmpty())
    try {
        if (this.width > 0 && this.height > 0)
            return Bitmap.createScaledBitmap(this, this.width, this.height, false)
        return this
    } catch (t: Throwable) {
        logger.severe(ExceptionUtils.getStackTrace(t))
    }
    return null
}