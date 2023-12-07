package com.kvsinyuk.stickergenerator.applicaiton.port

import java.awt.image.BufferedImage

interface ResizeImageService {

    fun resizeBufferedImage(image: BufferedImage): BufferedImage
}