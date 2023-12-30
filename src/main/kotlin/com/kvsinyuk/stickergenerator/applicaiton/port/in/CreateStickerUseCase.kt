package com.kvsinyuk.stickergenerator.applicaiton.port.`in`

import com.kvsinyuk.stickergenerator.domain.BotData

interface CreateStickerUseCase {

    fun createSticker(botData: BotData): ByteArray
}