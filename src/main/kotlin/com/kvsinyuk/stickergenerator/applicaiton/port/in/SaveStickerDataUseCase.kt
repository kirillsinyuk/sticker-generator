package com.kvsinyuk.stickergenerator.applicaiton.port.`in`

import com.kvsinyuk.stickergenerator.applicaiton.domain.BotData
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.SaveBotDataPort
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
