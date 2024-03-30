package com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo

import com.kvsinyuk.stickergenerator.adapter.out.mongo.StickerDataRepository
import com.kvsinyuk.stickergenerator.domain.BotData
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
