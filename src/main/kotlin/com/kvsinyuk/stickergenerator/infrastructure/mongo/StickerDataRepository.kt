package com.kvsinyuk.stickergenerator.infrastructure.mongo

import com.kvsinyuk.stickergenerator.applicaiton.domain.BotData
import org.springframework.data.mongodb.repository.MongoRepository

interface StickerDataRepository : MongoRepository<BotData, Long> {
    fun findByChatId(chatId: Long): BotData?
}
