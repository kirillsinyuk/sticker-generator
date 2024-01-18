package com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo

import com.kvsinyuk.stickergenerator.domain.BotData

interface FindBotDataPort {
    fun findByChatId(chatId: Long): BotData?
}
