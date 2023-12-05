package com.kvsinyuk.stickergenerator.applicaiton.port.`in`

import com.kvsinyuk.stickergenerator.domain.ImageData

interface CreateStickerUseCase {

    fun createSticker(imageData: ImageData): ByteArray
}