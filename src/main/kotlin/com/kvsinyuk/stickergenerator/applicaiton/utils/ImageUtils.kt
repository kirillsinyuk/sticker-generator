package com.kvsinyuk.stickergenerator.applicaiton.utils

import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

fun BufferedImage.toByteArray(): ByteArray =
    ByteArrayOutputStream().use {
        ImageIO.write(this, "png", it)
        it.toByteArray()
    }

fun ByteArray.toBufferedImage(): BufferedImage =
    ByteArrayInputStream(this)
        .use { ImageIO.read(it) }
