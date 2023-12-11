package com.kvsinyuk.stickergenerator.applicaiton.service

import com.kvsinyuk.stickergenerator.applicaiton.port.PadImageService
import com.kvsinyuk.stickergenerator.domain.StickerData
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.awt.image.BufferedImage

@Service
class PadImageServiceImpl(
    @Value("\${sticker.topPaddingPercent}")
    private val topPaddingPercent: Int
) : PadImageService {

    override fun addPaddingIfNecessary(stickerData: StickerData): BufferedImage {
        val image = stickerData.getBufferedImage()
        if (stickerData.topText.isBlank()) {
            return image
        }
        val topPaddingPixels = image.height.times(topPaddingPercent).div(100)

        return addTopPadding(image, topPaddingPixels)
    }

    private fun addTopPadding(image: BufferedImage, padding: Int): BufferedImage {
        val paddedImage = BufferedImage(image.width, image.height + padding, image.type)
        paddedImage.graphics
            .also { it.drawImage(image, 0, padding, null) }
            .also { it.dispose() }

        return paddedImage
    }
}