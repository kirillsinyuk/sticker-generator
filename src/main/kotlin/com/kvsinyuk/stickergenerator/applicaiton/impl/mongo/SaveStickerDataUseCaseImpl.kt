package com.kvsinyuk.stickergenerator.applicaiton.impl.mongo

import com.kvsinyuk.stickergenerator.adapter.out.mongo.StickerDataRepository
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.SaveStickerDataUseCase
import com.kvsinyuk.stickergenerator.domain.StickerData
import org.springframework.stereotype.Component

@Component
class SaveStickerDataUseCaseImpl(
    private val stickerDataRepository: StickerDataRepository
) : SaveStickerDataUseCase {

    override fun save(stickerData: StickerData) =
        stickerDataRepository.save(stickerData)
}