package com.kvsinyuk.stickergenerator.applicaiton.impl.mongo

import com.kvsinyuk.stickergenerator.adapter.out.mongo.StickerDataRepository
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindStickerDataUseCase
import org.springframework.stereotype.Component

@Component
class FindStickerDataUseCaseImpl(
    private val stickerDataRepository: StickerDataRepository
) : FindStickerDataUseCase {

    override fun findByChatId(chatId: Long) =
        stickerDataRepository.findByChatId(chatId)
}