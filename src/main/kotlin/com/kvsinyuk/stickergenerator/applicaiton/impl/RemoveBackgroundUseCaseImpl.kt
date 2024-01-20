package com.kvsinyuk.stickergenerator.applicaiton.impl

import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.RemoveBackgroundUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.http.RemoveBackgroundPort
import org.springframework.stereotype.Component
import java.awt.image.BufferedImage

@Component
class RemoveBackgroundUseCaseImpl(
    private val removeBackgroundPort: RemoveBackgroundPort,
) : RemoveBackgroundUseCase {
    override fun removeBackground(
        image: BufferedImage,
        originalFilename: String,
    ): BufferedImage {
        return removeBackgroundPort.removeBackground(image, originalFilename)
    }
}
