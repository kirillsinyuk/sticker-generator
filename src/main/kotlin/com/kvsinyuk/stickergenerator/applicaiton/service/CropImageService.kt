package com.kvsinyuk.stickergenerator.applicaiton.service

import com.kvsinyuk.stickergenerator.domain.BotData

interface CropImageService {

    fun cropImage(botData: BotData): BotData
}