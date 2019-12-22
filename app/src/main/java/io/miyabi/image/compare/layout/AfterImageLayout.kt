package io.miyabi.image.compare.layout

interface AfterImageLayout {
    fun onLoaded(success: Boolean)
    fun textVisible(progress: Int)
}