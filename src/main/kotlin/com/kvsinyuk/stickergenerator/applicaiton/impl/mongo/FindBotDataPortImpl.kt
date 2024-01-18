package com.kvsinyuk.stickergenerator.applicaiton.impl.mongo

import com.kvsinyuk.stickergenerator.adapter.out.mongo.StickerDataRepository
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import org.springframework.stereotype.Component

@Component
class FindBotDataPortImpl(
    private val stickerDataRepository: StickerDataRepository,
) : FindBotDataPort {
    override fun findByChatId(chatId: Long) = stickerDataRepository.findByChatId(chatId)
}
