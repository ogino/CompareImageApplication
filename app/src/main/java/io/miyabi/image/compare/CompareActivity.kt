package io.miyabi.image.compare

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import io.miyabi.image.compare.layout.CompareLayout

class CompareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare)

        window.decorView.systemUiVisibility = (
            View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        )
        actionBar?.hide()

        val layout = findViewById<CompareLayout>(R.id.compare_layout)
//        layout.putBefore(ResourcesCompat.getDrawable(resources, R.drawable.before, null))
//            .putAfter(ResourcesCompat.getDrawable(resources, R.drawable.after, null))
//        layout.putBefore("https://cdn.hasselblad.com/samples/x1d-II-50c/x1d-II-sample-01.jpg")
//            .putAfter("https://cdn.hasselblad.com/samples/x1d-II-50c/x1d-II-sample-02.jpg")
//        layout.putBefore("https://www.publicdomainpictures.net/pictures/10000/velka/947-1262213425CFHP.jpg")
//            .putAfter("https://www.publicdomainpictures.net/pictures/80000/velka/sunset-from-sandpatch.jpg")
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility = (
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    )
            actionBar?.hide()
        }
    }


}