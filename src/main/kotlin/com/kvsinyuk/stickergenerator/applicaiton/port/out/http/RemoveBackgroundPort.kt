package com.kvsinyuk.stickergenerator.applicaiton.port.out.http

import com.kvsinyuk.stickergenerator.domain.BotData

interface RemoveBackgroundPort {

    fun removeBackground(botData: BotData): BotData
}
