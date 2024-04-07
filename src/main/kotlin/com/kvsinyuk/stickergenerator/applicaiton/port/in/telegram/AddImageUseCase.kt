package com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram

import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.SaveStickerDataUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.GetBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramFilePort
import com.kvsinyuk.stickergenerator.domain.BotData
import org.springframework.stereotype.Component

interface AddImageUseCase {
    fun addImage(command: AddImageCommand): BotData

    data class AddImageCommand(
        val chatId: Long,
        val fileId: String,
        val originalFilename: String,
    )
}

@Component
class AddImageUseCaseImpl(
    private val telegramFilePort: TelegramFilePort,
    private val getBotDataPort: GetBotDataPort,
    private val saveStickerDataUseCase: SaveStickerDataUseCase,
) : AddImageUseCase {
    override fun addImage(command: AddImageUseCase.AddImageCommand): BotData {
        val file = telegramFilePort.getFileContent(command.fileId)
        return getBotDataPort.getByChatId(command.chatId)
            .addImage(file, command.originalFilename)
            .let { saveStickerDataUseCase.save(it) }
    }
}
