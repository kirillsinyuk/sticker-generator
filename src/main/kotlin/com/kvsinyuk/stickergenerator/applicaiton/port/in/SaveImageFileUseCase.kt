package com.kvsinyuk.stickergenerator.applicaiton.port.`in`

import com.kvsinyuk.stickergenerator.domain.StickerData
import com.pengrad.telegrambot.model.Document

interface SaveImageFileUseCase {

    fun saveFile(chatId: Long, document: Document): StickerData
}