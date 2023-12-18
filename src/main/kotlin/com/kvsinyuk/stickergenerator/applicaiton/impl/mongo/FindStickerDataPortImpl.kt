package com.kvsinyuk.stickergenerator.applicaiton.impl.mongo

import com.kvsinyuk.stickergenerator.adapter.out.mongo.StickerDataRepository
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindStickerDataPort
import org.springframework.stereotype.Component

@Component
class FindStickerDataPortImpl(
    private val stickerDataRepository: StickerDataRepository
) : FindStickerDataPort {

    override fun findByChatId(chatId: Long) =
        stickerDataRepository.findByChatId(chatId)
}