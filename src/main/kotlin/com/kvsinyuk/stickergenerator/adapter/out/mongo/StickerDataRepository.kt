package com.kvsinyuk.stickergenerator.adapter.out.mongo

import com.kvsinyuk.stickergenerator.domain.StickerData
import org.springframework.data.mongodb.repository.MongoRepository

interface StickerDataRepository: MongoRepository<StickerData, Long> {

    fun findByChatId(chatId: Long): StickerData?
}