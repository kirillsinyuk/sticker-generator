package com.kvsinyuk.stickergenerator.applicaiton.service

import java.awt.image.BufferedImage

interface PadImageService {
    fun addPaddingIfNecessary(
        image: BufferedImage,
        hasTopText: Boolean,
    ): BufferedImage
}
