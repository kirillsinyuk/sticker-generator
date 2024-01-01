package com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo

import com.kvsinyuk.stickergenerator.domain.BotData

interface GetBotDataPort {

    fun getByChatId(chatId: Long): BotData
}