package com.kvsinyuk.stickergenerator.domain

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

@Document
data class BotData(
    @Id
    var chatId: Long,
    var status: Status,

    var image: ByteArray = ByteArray(0),
    var originalFilename: String = "",
    var topText: String = "",
    var bottomText: String = "",

    var sourceImage: ByteArray = ByteArray(0),
    var targetImage: ByteArray = ByteArray(0)
) {

    fun getBufferedImage(): BufferedImage =
        ByteArrayInputStream(image)
            .use { ImageIO.read(it) }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BotData

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
        result = 31 * result + (chatId.hashCode())
        return result
    }
}