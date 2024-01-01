package com.kvsinyuk.stickergenerator.applicaiton.service

import com.kvsinyuk.stickergenerator.domain.BotData
import java.awt.image.BufferedImage

interface PadImageService {

    fun addPaddingIfNecessary(botData: BotData): BufferedImage
}