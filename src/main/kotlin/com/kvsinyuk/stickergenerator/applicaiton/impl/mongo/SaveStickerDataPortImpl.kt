package com.kvsinyuk.stickergenerator.applicaiton.impl.mongo

import com.kvsinyuk.stickergenerator.adapter.out.mongo.StickerDataRepository
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.SaveStickerDataPort
import com.kvsinyuk.stickergenerator.domain.StickerData
import org.springframework.stereotype.Component

@Component
class SaveStickerDataPortImpl(
    private val stickerDataRepository: StickerDataRepository
) : SaveStickerDataPort {

    override fun save(stickerData: StickerData) =
        stickerDataRepository.save(stickerData)
}