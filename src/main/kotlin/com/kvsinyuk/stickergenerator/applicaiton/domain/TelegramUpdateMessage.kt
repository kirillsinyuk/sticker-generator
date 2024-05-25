package com.kvsinyuk.stickergenerator.applicaiton.domain

import com.pengrad.telegrambot.model.Document
import com.pengrad.telegrambot.model.PhotoSize

data class TelegramUpdateMessage(
    val message: String?,
    val chatId: Long,
    val document: Document?,
    val photos: Array<PhotoSize>?,
)
