package com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo

import com.kvsinyuk.stickergenerator.adapter.out.mongo.StickerDataRepository
import org.springframework.stereotype.Component

interface DeleteBotDataPort {
    fun delete(chatId: Long)
}

@Component
class DeleteBotDataPortImpl(
    private val stickerDataRepository: StickerDataRepository,
) : DeleteBotDataPort {
    override fun delete(chatId: Long) = stickerDataRepository.deleteById(chatId)
}
