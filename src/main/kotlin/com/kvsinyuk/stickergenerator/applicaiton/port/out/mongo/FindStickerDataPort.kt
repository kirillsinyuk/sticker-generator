package com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo

import com.kvsinyuk.stickergenerator.domain.StickerData

interface FindStickerDataPort {

    fun findByChatId(chatId: Long): StickerData?
}