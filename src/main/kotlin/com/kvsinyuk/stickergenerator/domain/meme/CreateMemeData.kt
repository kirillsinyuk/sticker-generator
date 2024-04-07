package com.kvsinyuk.stickergenerator.domain.meme

import com.kvsinyuk.stickergenerator.domain.CommandData
import org.springframework.data.annotation.TypeAlias

@TypeAlias("create_meme")
data class CreateMemeData(
    var status: MemeStatus = MemeStatus.INIT,
    var originalFilename: String = "result.png",
    var topText: String = "",
    var bottomText: String = "",
) : CommandData() {
    var image: ByteArray? = null

    override fun isMemeData() = true

    fun addText(
        text: String,
        isTop: Boolean,
    ) {
        if (isTop) {
            status = MemeStatus.TOP_TEXT_ADDED
            topText = text.takeIf { it != "*" } ?: ""
        } else {
            bottomText = text.takeIf { it != "*" } ?: ""
        }
    }

    fun addImage(
        file: ByteArray,
        fileName: String,
    ) {
        status = MemeStatus.SOURCE_FILE_ADDED
        image = file
        originalFilename = fileName
    }
}
