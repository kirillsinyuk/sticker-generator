package com.kvsinyuk.stickergenerator.applicaiton.domain

import com.pengrad.telegrambot.model.Document
import com.pengrad.telegrambot.model.PhotoSize
import com.pengrad.telegrambot.model.Sticker

data class TelegramUpdateMessage(
    val message: String?,
    val chatId: Long,
    val document: Document?,
    val sticker: Sticker?,
    val photos: Array<PhotoSize>?,
)
