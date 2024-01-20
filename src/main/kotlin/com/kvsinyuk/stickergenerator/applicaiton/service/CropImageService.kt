package com.kvsinyuk.stickergenerator.applicaiton.service

import java.awt.image.BufferedImage

interface CropImageService {
    fun cropImage(image: BufferedImage): BufferedImage
}
