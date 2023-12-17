package com.kvsinyuk.stickergenerator.applicaiton.impl.mongo

import com.kvsinyuk.stickergenerator.adapter.out.mongo.StickerDataRepository
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.DeleteStickerDataUseCase
import org.springframework.stereotype.Component

@Component
class DeleteStickerDataUseCaseImpl(
    private val stickerDataRepository: StickerDataRepository
) : DeleteStickerDataUseCase {

    override fun delete(chatId: Long) =
        stickerDataRepository.deleteById(chatId)
}