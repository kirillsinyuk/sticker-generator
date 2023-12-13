package com.kvsinyuk.stickergenerator.applicaiton.impl

import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.SaveImageFileUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramFilePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.SaveStickerDataUseCase
import com.kvsinyuk.stickergenerator.domain.StickerData
import com.pengrad.telegrambot.model.Document
import org.springframework.stereotype.Component

@Component
class SaveImageFileUseCaseImpl(
    private val telegramFilePort: TelegramFilePort,
    private val saveStickerDataUseCase: SaveStickerDataUseCase
) : SaveImageFileUseCase {

    override fun saveFile(chatId: Long, document: Document): StickerData {
        val fileContent = telegramFilePort.getFileContent(document.fileId())
        val stickerData = StickerData(fileContent, document.fileName(), chatId = chatId)
        return saveStickerDataUseCase.save(stickerData)
    }
}