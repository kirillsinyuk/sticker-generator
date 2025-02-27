package com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram

import com.kvsinyuk.stickergenerator.applicaiton.port.out.http.RemoveBackgroundPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramFilePort
import com.kvsinyuk.stickergenerator.applicaiton.service.CropImageService
import com.kvsinyuk.stickergenerator.applicaiton.utils.toBufferedImage
import com.pengrad.telegrambot.model.Document
import org.springframework.stereotype.Component
import java.awt.image.BufferedImage

interface RemoveBackgroundUseCase {
    fun removeBackground(command: RemoveBackgroundCommand): BufferedImage

    data class RemoveBackgroundCommand(
        val chatId: Long,
        val fileId: String,
        val originalFilename: String,
    ) {
        constructor(chatId: Long, document: Document) : this(chatId, document.fileId(), document.fileName())
    }
}

@Component
class RemoveBackgroundUseCaseImpl(
    private val removeBackgroundPort: RemoveBackgroundPort,
    private val cropImageService: CropImageService,
    private val telegramFilePort: TelegramFilePort,
) : RemoveBackgroundUseCase {
    override fun removeBackground(command: RemoveBackgroundUseCase.RemoveBackgroundCommand) =
        telegramFilePort.getFileContent(command.fileId)
            .let { removeBackgroundPort.removeBackground(it.image.toBufferedImage(), command.originalFilename) }
            .let { cropImageService.cropImage(it) }
}
