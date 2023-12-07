package com.kvsinyuk.stickergenerator.applicaiton.service

import com.kvsinyuk.stickergenerator.applicaiton.port.ResizeImageService
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.awt.image.BufferedImage

@Service
class ResizeImageServiceImpl(
    @Value("\${sticker.maxPixelSize}")
    private val maxDesiredSize: Int
) : ResizeImageService {

    override fun resizeBufferedImage(image: BufferedImage): BufferedImage {
        val scaleFactor = getScaleFactor(image.width, image.height)
        return Resizer.PROGRESSIVE_BILINEAR.resize(
            image,
            image.width.times(scaleFactor).toInt(),
            image.height.times(scaleFactor).toInt()
        )
    }

    private fun getScaleFactor(originalWidth: Int, originalHeight: Int): Float {
        val max = originalHeight.coerceAtLeast(originalWidth)
        return maxDesiredSize.div(max.toFloat())
    }
}