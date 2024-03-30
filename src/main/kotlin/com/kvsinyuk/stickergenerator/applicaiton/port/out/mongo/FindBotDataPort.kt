package com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo

import com.kvsinyuk.stickergenerator.adapter.out.mongo.StickerDataRepository
import com.kvsinyuk.stickergenerator.domain.BotData
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
