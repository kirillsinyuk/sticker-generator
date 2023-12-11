package com.kvsinyuk.stickergenerator.applicaiton.port

import com.kvsinyuk.stickergenerator.domain.StickerData
import java.awt.image.BufferedImage

interface PadImageService {

    fun addPaddingIfNecessary(stickerData: StickerData): BufferedImage
}