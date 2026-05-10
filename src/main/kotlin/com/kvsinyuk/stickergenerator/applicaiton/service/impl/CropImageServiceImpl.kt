package com.kvsinyuk.stickergenerator.applicaiton.service.impl

import com.kvsinyuk.stickergenerator.applicaiton.service.CropImageService
import org.springframework.stereotype.Service
import java.awt.image.BufferedImage

@Service
class CropImageServiceImpl : CropImageService {
    override fun cropImage(image: BufferedImage) = crop(image)

    private fun crop(image: BufferedImage): BufferedImage {
        val width = image.width
        val height = image.height
        val pixels = image.getRGB(0, 0, width, height, null, 0, width)
        var minX = Int.MAX_VALUE
        var minY = Int.MAX_VALUE
        var maxX = -1
        var maxY = -1
        for (y in 0 until height) {
            val rowOffset = y * width
            for (x in 0 until width) {
                if ((pixels[rowOffset + x] ushr 24) and 0xFF != 0) {
                    if (x < minX) minX = x
                    if (x > maxX) maxX = x
                    if (y < minY) minY = y
                    if (y > maxY) maxY = y
                }
            }
        }
        if (maxX < 0) return image
        return image.getSubimage(minX, minY, maxX - minX + 1, maxY - minY + 1)
    }
}
