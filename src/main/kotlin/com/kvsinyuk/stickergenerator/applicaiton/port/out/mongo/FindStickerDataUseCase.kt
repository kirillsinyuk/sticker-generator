package com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo

import com.kvsinyuk.stickergenerator.domain.StickerData

interface FindStickerDataUseCase {

    fun findByChatId(chatId: Long): StickerData?
}