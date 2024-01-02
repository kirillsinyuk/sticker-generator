package com.kvsinyuk.stickergenerator.applicaiton.impl

import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.SaveStickerDataUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.SaveBotDataPort
import com.kvsinyuk.stickergenerator.domain.BotData
import org.springframework.stereotype.Component

@Component
class SaveStickerDataUseCaseImpl(
    private val saveBotDataPort: SaveBotDataPort
) : SaveStickerDataUseCase {

    override fun save(data: BotData): BotData {
        return saveBotDataPort.save(data)
    }
}