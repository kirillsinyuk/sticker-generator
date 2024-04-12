package com.kvsinyuk.stickergenerator.domain.sticker

import com.kvsinyuk.stickergenerator.domain.CommandData
import com.kvsinyuk.stickergenerator.domain.Image
import org.springframework.data.annotation.TypeAlias

@TypeAlias("create_sticker")
data class CreateStickerData(
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

    fun addImage(
        file: ByteArray,
        fileName: String,
    ) {
        status = StickerStatus.SOURCE_FILE_ADDED
        image = Image(file, fileName)
    }
}
