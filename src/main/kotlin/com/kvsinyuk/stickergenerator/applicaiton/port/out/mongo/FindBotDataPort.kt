package com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo

import com.kvsinyuk.stickergenerator.applicaiton.domain.BotData
import com.kvsinyuk.stickergenerator.infrastructure.mongo.StickerDataRepository
import org.springframework.stereotype.Component

interface FindBotDataPort {
    fun findByChatId(chatId: Long): BotData?
}

@Component
class FindBotDataPortImpl(
    private val stickerDataRepository: StickerDataRepository,
) : FindBotDataPort {
    override fun findByChatId(chatId: Long) = stickerDataRepository.findByChatId(chatId)
}
