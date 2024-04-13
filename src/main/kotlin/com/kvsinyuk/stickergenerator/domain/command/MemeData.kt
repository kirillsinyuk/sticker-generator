package com.kvsinyuk.stickergenerator.domain.command

import com.kvsinyuk.stickergenerator.domain.Image
import org.springframework.data.annotation.TypeAlias

@TypeAlias("create_meme")
data class MemeData(
    var status: MemeStatus = MemeStatus.INIT,
    var topText: String = "",
    var bottomText: String = "",
) : CommandData() {
    lateinit var image: Image

    override fun isMemeData() = true

    override fun getSourceImage() = image

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
        image = Image(file, fileName)
    }
}

enum class MemeStatus {
    INIT,
    SOURCE_FILE_ADDED,
    TOP_TEXT_ADDED,
}