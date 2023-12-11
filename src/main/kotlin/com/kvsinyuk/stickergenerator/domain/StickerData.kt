package com.kvsinyuk.stickergenerator.domain

import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

data class StickerData(
    var image: ByteArray,
    val originalFilename: String,
    val topText: String,
    val bottomText: String
) {

    fun getBufferedImage(): BufferedImage =
        ByteArrayInputStream(image)
            .use { ImageIO.read(it) }
}