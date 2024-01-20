package com.kvsinyuk.stickergenerator.applicaiton.port.out.http

import java.awt.image.BufferedImage

interface RemoveBackgroundPort {
    fun removeBackground(
        image: BufferedImage,
        originalFilename: String,
    ): BufferedImage
}
