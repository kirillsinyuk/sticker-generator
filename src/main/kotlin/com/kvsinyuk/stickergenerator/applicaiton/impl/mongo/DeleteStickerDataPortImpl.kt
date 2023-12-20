package com.kvsinyuk.stickergenerator.applicaiton.impl.mongo

import com.kvsinyuk.stickergenerator.adapter.out.mongo.StickerDataRepository
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.DeleteStickerDataPort
import org.springframework.stereotype.Component

@Component
class DeleteStickerDataPortImpl(
    private val stickerDataRepository: StickerDataRepository
) : DeleteStickerDataPort {

    override fun delete(chatId: Long) =
        stickerDataRepository.deleteById(chatId)
}