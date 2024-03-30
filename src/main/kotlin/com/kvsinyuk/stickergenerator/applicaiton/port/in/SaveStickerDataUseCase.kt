package com.kvsinyuk.stickergenerator.applicaiton.port.`in`

import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.SaveBotDataPort
import com.kvsinyuk.stickergenerator.domain.BotData
import org.springframework.stereotype.Component

interface SaveStickerDataUseCase {
    fun save(data: BotData): BotData
}

@Component
class SaveStickerDataUseCaseImpl(
    private val saveBotDataPort: SaveBotDataPort,
) : SaveStickerDataUseCase {
    override fun save(data: BotData): BotData {
        return saveBotDataPort.save(data)
    }
}
