package com.kvsinyuk.stickergenerator.service

import org.springframework.stereotype.Service
import java.awt.image.BufferedImage
import java.awt.image.Raster

@Service
class CropImageService {

    fun cropImage(image: BufferedImage): BufferedImage {
        var minY = 0; var maxY = 0
        var minX = Int.MAX_VALUE; var maxX = 0
        var isBlank: Boolean
        var minYIsDefined = false
        val raster: Raster = image.raster
        for (y in 0 until image.height) {
            isBlank = true
            for (x in 0 until image.width) {
                if (raster.getSample(x, y, 0) != 0) {
                    isBlank = false
                    if (x < minX) minX = x
                    if (x > maxX) maxX = x
                }
            }
            if (!isBlank) {
                if (!minYIsDefined) {
                    minY = y
                    minYIsDefined = true
                } else {
                    if (y > maxY) maxY = y
                }
            }
        }
        return image.getSubimage(minX, minY, maxX - minX + 1, maxY - minY + 1)
    }
}