package com.kvsinyuk.stickergenerator.applicaiton.service

import java.awt.image.BufferedImage

interface ResizeImageService {
    fun resizeBufferedImage(image: BufferedImage): BufferedImage
}
