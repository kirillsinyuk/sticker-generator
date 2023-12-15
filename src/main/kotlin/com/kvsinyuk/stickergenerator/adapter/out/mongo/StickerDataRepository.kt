package com.kvsinyuk.stickergenerator.adapter.out.mongo

import com.kvsinyuk.stickergenerator.domain.StickerData
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface StickerDataRepository: MongoRepository<StickerData, ObjectId> {

    fun findByChatId(chatId: Long): StickerData?
}