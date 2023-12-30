package com.kvsinyuk.stickergenerator.applicaiton.impl

import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.SaveStickerDataUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.SaveStickerDataPort
import com.kvsinyuk.stickergenerator.domain.BotData
import org.springframework.stereotype.Component

@Component
class SaveStickerDataUseCaseImpl(
    private val saveStickerDataPort: SaveStickerDataPort
) : SaveStickerDataUseCase {

    override fun save(data: BotData): BotData {
        return saveStickerDataPort.save(data)
    }
}