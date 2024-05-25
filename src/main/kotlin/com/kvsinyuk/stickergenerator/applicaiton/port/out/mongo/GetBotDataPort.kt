package com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo

import com.kvsinyuk.stickergenerator.applicaiton.domain.BotData
import com.kvsinyuk.stickergenerator.applicaiton.exception.DataNotFoundException
import com.kvsinyuk.stickergenerator.infrastructure.mongo.StickerDataRepository
import org.springframework.stereotype.Component

interface GetBotDataPort {
    fun getByChatId(chatId: Long): BotData
}

@Component
class GetBotDataPortImpl(
    private val stickerDataRepository: StickerDataRepository,
) : GetBotDataPort {
    override fun getByChatId(chatId: Long) =
        stickerDataRepository.findByChatId(chatId)
            ?: throw DataNotFoundException(chatId)
}
