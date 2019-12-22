package io.miyabi.image.compare

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import io.miyabi.image.compare.layout.CompareLayout

class CompareActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_compare)

        val layout = findViewById<CompareLayout>(R.id.compare_layout)
//        layout.putBefore(ResourcesCompat.getDrawable(resources, R.drawable.before, null))
//            .putAfter(ResourcesCompat.getDrawable(resources, R.drawable.after, null))
//        layout.putBefore("https://cdn.hasselblad.com/samples/x1d-II-50c/x1d-II-sample-01.jpg")
//            .putAfter("https://cdn.hasselblad.com/samples/x1d-II-50c/x1d-II-sample-02.jpg")
//        layout.putBefore("https://www.publicdomainpictures.net/pictures/10000/velka/947-1262213425CFHP.jpg")
//            .putAfter("https://www.publicdomainpictures.net/pictures/80000/velka/sunset-from-sandpatch.jpg")
    }


}