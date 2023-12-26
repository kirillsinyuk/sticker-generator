package com.kvsinyuk.stickergenerator.applicaiton.service.impl

import com.kvsinyuk.stickergenerator.applicaiton.service.ResizeImageService
import com.kvsinyuk.stickergenerator.applicaiton.service.Resizer
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.awt.image.BufferedImage
import kotlin.math.roundToInt

@Service
class ResizeImageServiceImpl(
    @Value("\${sticker.maxPixelSize}")
    private val maxDesiredSize: Int
) : ResizeImageService {

    override fun resizeBufferedImage(image: BufferedImage): BufferedImage {
        val scaleFactor = getScaleFactor(image.width, image.height)
        return Resizer.PROGRESSIVE_BILINEAR.resize(
            image,
            image.width.times(scaleFactor).roundToInt(),
            image.height.times(scaleFactor).roundToInt()
        )
    }

    private fun getScaleFactor(originalWidth: Int, originalHeight: Int): Float {
        val max = originalHeight.coerceAtLeast(originalWidth).toFloat()
        return maxDesiredSize.div(max)
    }
}