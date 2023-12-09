package com.kvsinyuk.stickergenerator.applicaiton.port

import com.kvsinyuk.stickergenerator.domain.StickerData
import java.awt.image.BufferedImage

interface CropImageService {

    fun cropImage(image: StickerData): BufferedImage
}