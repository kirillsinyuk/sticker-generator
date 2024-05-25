package com.kvsinyuk.stickergenerator.applicaiton.domain.command

import com.kvsinyuk.stickergenerator.applicaiton.domain.Image
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

    fun addImage(image: Image) {
        status = MemeStatus.SOURCE_FILE_ADDED
        this.image = image
    }
}

enum class MemeStatus {
    INIT,
    SOURCE_FILE_ADDED,
    TOP_TEXT_ADDED,
}
