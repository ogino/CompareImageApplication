package io.miyabi.image.compare.task

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ClipDrawable
import android.os.AsyncTask
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import com.nostra13.universalimageloader.core.ImageLoader
import io.miyabi.image.compare.R
import io.miyabi.image.compare.extension.scaledBitmap
import org.apache.commons.lang3.exception.ExceptionUtils
import java.lang.ref.WeakReference
import java.util.logging.Logger

class ClipDrawableTask<T>(imageView: ImageView,
                          seekBar: SeekBar,
                          private val context: Context,
                          private val listener: AfterImage? = null) : AsyncTask<T, Void, ClipDrawable>() {

    private val logger = Logger.getLogger(javaClass.canonicalName.orEmpty())
    private val imageRef = WeakReference(imageView)
    private val seekBarRef = WeakReference(seekBar)
    private val imageLoader = ImageLoader.getInstance()

    override fun doInBackground(vararg params: T): ClipDrawable {
        Looper.myLooper()?.let {
            Looper.prepare()
        }
        try {
            var theBitmap: Bitmap
                theBitmap = if (params[0] is String) imageLoader.loadImageSync(params[0].toString())
                else (params[0] as BitmapDrawable).bitmap
            theBitmap.scaledBitmap()?.let {
                theBitmap = it
            }
            return ClipDrawable(BitmapDrawable(context.resources, theBitmap), Gravity.START, ClipDrawable.HORIZONTAL)
        } catch (t: Throwable) {
            logger.severe(ExceptionUtils.getStackTrace(t))
        }
        return ClipDrawable(null, 0, 0)
    }

    private val progressNum = 5000

    override fun onPostExecute(clipDrawable: ClipDrawable?) {
        val image = imageRef.get()
        image?.let {
            clipDrawable?.let {
                initSeekBar(clipDrawable)
                image.setImageDrawable(clipDrawable)
                when (clipDrawable.level) {
                    0 -> clipDrawable.level = seekBarRef.get()!!.progress
                    else -> clipDrawable.level = progressNum
                }
                listener?.onLoaded(true)
                return
            }
        }
        listener?.onLoaded(false)
    }

    private fun initSeekBar(clipDrawable: ClipDrawable?) {
        seekBarRef.get()!!.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                clipDrawable?.level = progress
                listener?.textVisible(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }

        })
    }

    interface AfterImage {
        fun onLoaded(success: Boolean)
        fun textVisible(progress: Int)
    }

}