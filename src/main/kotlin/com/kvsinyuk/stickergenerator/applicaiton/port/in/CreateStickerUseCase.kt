package com.kvsinyuk.stickergenerator.applicaiton.port.`in`

import com.kvsinyuk.stickergenerator.domain.StickerData

interface CreateStickerUseCase {

    fun createSticker(stickerData: StickerData): ByteArray
}