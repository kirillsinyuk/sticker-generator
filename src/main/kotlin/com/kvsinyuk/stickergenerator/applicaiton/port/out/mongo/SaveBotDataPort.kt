package com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo

import com.kvsinyuk.stickergenerator.applicaiton.domain.BotData
import com.kvsinyuk.stickergenerator.infrastructure.mongo.StickerDataRepository
import org.springframework.stereotype.Component

interface SaveBotDataPort {
    fun save(botData: BotData): BotData
}

@Component
class SaveBotDataPortImpl(
    private val stickerDataRepository: StickerDataRepository,
) : SaveBotDataPort {
    override fun save(botData: BotData) = stickerDataRepository.save(botData)
}
