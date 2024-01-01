package com.kvsinyuk.stickergenerator.applicaiton.port.`in`

import com.kvsinyuk.stickergenerator.domain.BotData

interface SaveStickerDataUseCase {

    fun save(data: BotData): BotData
}