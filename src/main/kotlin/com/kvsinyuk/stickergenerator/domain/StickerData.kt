package com.kvsinyuk.stickergenerator.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

@Document
data class StickerData(
    var image: ByteArray,
    val originalFilename: String,
    val topText: String = "",
    val bottomText: String = "",
    @Id
    var chatId: Long? = null,
    var status: Status = Status.FILE_ADDED,
) {

    fun getBufferedImage(): BufferedImage =
        ByteArrayInputStream(image)
            .use { ImageIO.read(it) }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as StickerData

        if (originalFilename != other.originalFilename) return false
        if (topText != other.topText) return false
        if (bottomText != other.bottomText) return false
        if (chatId != other.chatId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = originalFilename.hashCode()
        result = 31 * result + topText.hashCode()
        result = 31 * result + bottomText.hashCode()
        result = 31 * result + (chatId?.hashCode() ?: 0)
        return result
    }
}