package com.kvsinyuk.stickergenerator.applicaiton.domain.command

import com.kvsinyuk.stickergenerator.applicaiton.domain.Image
import org.springframework.data.annotation.TypeAlias

@TypeAlias("create_sticker")
data class StickerData(
    var status: StickerStatus = StickerStatus.INIT,
    var topText: String = "",
    var bottomText: String = "",
) : CommandData() {
    lateinit var image: Image

    override fun isStickerData() = true

    override fun getSourceImage() = image

    fun addText(
        text: String,
        isTop: Boolean,
    ) {
        if (isTop) {
            status = StickerStatus.TOP_TEXT_ADDED
            topText = text.takeIf { it != "*" } ?: ""
        } else {
            bottomText = text.takeIf { it != "*" } ?: ""
        }
    }

    fun addImage(image: Image) {
        status = StickerStatus.SOURCE_FILE_ADDED
        this.image = image
    }
}

enum class StickerStatus {
    INIT,
    SOURCE_FILE_ADDED,
    TOP_TEXT_ADDED,
}
