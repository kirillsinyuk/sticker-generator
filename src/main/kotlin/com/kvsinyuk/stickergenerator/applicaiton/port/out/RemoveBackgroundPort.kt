package com.kvsinyuk.stickergenerator.applicaiton.port.out

import com.kvsinyuk.stickergenerator.domain.ImageData

interface RemoveBackgroundPort {

    fun removeBackground(imageData: ImageData): ImageData
}
