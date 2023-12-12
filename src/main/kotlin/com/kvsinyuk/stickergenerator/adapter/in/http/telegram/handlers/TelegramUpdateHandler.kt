package com.kvsinyuk.stickergenerator.adapter.`in`.http.telegram.handlers

import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage

interface TelegramUpdateHandler {
    fun process(update: TelegramUpdateMessage)

    fun canApply(update: TelegramUpdateMessage): Boolean
}
