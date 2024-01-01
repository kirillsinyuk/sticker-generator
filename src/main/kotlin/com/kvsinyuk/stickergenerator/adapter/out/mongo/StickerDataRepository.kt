package com.kvsinyuk.stickergenerator.adapter.out.mongo

import com.kvsinyuk.stickergenerator.domain.BotData
import org.springframework.data.mongodb.repository.MongoRepository

interface StickerDataRepository: MongoRepository<BotData, Long> {

    fun findByChatId(chatId: Long): BotData?
}