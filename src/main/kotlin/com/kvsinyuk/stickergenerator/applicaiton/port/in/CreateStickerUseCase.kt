package com.kvsinyuk.stickergenerator.applicaiton.port.`in`

import com.kvsinyuk.stickergenerator.domain.BotData
import java.awt.image.BufferedImage

interface CreateStickerUseCase {
    fun createSticker(botData: BotData): BufferedImage
}
