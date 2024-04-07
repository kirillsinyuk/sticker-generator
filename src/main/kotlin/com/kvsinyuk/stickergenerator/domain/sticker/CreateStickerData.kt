package com.kvsinyuk.stickergenerator.domain.sticker

import com.kvsinyuk.stickergenerator.domain.CommandData
import org.springframework.data.annotation.TypeAlias

@TypeAlias("create_sticker")
data class CreateStickerData(
    var status: StickerStatus = StickerStatus.INIT,
    var originalFilename: String = "result.png",
    var topText: String = "",
    var bottomText: String = "",
) : CommandData() {
    var image: ByteArray? = null

    override fun isStickerData() = true

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
        image = file
        originalFilename = fileName
    }
}
