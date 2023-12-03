package com.kvsinyuk.stickergenerator.applicaiton.impl

import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.CreateStickerUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.RemoveBackgroundPort
import com.kvsinyuk.stickergenerator.applicaiton.service.CropImageService
import com.kvsinyuk.stickergenerator.domain.ImageData
import org.springframework.stereotype.Component

@Component
class CreateStickerUseCaseImpl(
    private val removeBackgroundPort: RemoveBackgroundPort,
    private val cropImageService: CropImageService
) : CreateStickerUseCase {

    override fun createSticker(imageData: ImageData): ByteArray {
        return removeBackgroundPort.removeBackground(imageData)
            .let { cropImageService.cropImage(it) }
            .image
    }
}