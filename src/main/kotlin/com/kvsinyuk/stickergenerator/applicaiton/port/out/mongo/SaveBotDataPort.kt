package com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo

import com.kvsinyuk.stickergenerator.domain.BotData

interface SaveBotDataPort {

    fun save(botData: BotData): BotData
}