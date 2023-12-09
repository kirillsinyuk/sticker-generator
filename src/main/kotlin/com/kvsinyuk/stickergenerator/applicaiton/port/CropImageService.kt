package com.kvsinyuk.stickergenerator.applicaiton.port

import com.kvsinyuk.stickergenerator.domain.ImageData

interface CropImageService {

    fun cropImage(image: ImageData): ImageData
}