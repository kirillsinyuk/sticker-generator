package com.kvsinyuk.stickergenerator.applicaiton.impl.mongo

import com.kvsinyuk.stickergenerator.adapter.out.mongo.StickerDataRepository
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.DeleteBotDataPort
import org.springframework.stereotype.Component

@Component
class DeleteBotDataPortImpl(
    private val stickerDataRepository: StickerDataRepository,
) : DeleteBotDataPort {
    override fun delete(chatId: Long) = stickerDataRepository.deleteById(chatId)
}
