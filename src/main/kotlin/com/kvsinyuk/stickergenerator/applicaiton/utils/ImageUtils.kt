package com.kvsinyuk.stickergenerator.applicaiton.utils

import com.kvsinyuk.stickergenerator.domain.ImageData
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

fun ImageData.mapToBufferedImage(): BufferedImage {
    return ByteArrayInputStream(this.image)
        .use { ImageIO.read(it) }
}

fun BufferedImage.mapToByteArray(): ByteArray =
    ByteArrayOutputStream().use {
        ImageIO.write(this, "png", it)
        it.toByteArray()
    }