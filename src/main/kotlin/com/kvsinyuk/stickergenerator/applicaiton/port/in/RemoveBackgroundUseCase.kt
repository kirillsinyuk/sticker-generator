package com.kvsinyuk.stickergenerator.applicaiton.port.`in`

import java.awt.image.BufferedImage

interface RemoveBackgroundUseCase {

    fun removeBackground(image: BufferedImage, originalFilename: String): BufferedImage
}