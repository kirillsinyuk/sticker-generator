package com.kvsinyuk.stickergenerator.domain.sticker

import com.kvsinyuk.stickergenerator.domain.CommandData
import org.springframework.data.annotation.TypeAlias

@TypeAlias("create_sticker")
data class CreateStickerData(
    var status: StickerStatus = StickerStatus.INIT,
    var originalFilename: String = "result.png",
    var topText: String = "",
    var bottomText: String = ""
): CommandData() {

    var image: ByteArray? = null

    override fun isStickerData() = true
}