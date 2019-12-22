package io.miyabi.image.compare.extension

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.squareup.picasso.Picasso
import org.apache.commons.lang3.exception.ExceptionUtils
import java.util.logging.Logger

fun ImageView.loadImage(url: String?) {
    Picasso.get().load(url).into(this)
}

fun ImageView.loadImage(drawable: Drawable?) {
    this.setImageDrawable(drawable)
}

fun View.visible(visible: Boolean) {
    this.visibility = if (visible) View.VISIBLE else View.GONE
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