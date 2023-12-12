package com.kvsinyuk.stickergenerator.domain

data class TelegramUpdateMessage(
    val message: String,
    var chatId: Long
)