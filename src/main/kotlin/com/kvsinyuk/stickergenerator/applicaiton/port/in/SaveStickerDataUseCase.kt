package com.kvsinyuk.stickergenerator.applicaiton.port.`in`

import com.kvsinyuk.stickergenerator.domain.StickerData

interface SaveStickerDataUseCase {

    fun save(data: StickerData): StickerData
}