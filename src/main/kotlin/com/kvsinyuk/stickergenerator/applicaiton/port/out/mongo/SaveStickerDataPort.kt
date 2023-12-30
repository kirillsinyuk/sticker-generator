package com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo

import com.kvsinyuk.stickergenerator.domain.BotData

interface SaveStickerDataPort {

    fun save(botData: BotData): BotData
}