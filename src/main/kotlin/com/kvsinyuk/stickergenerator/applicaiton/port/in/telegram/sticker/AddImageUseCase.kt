package com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.sticker

import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.SaveStickerDataUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.GetBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramFilePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.domain.sticker.StickerStatus
import org.springframework.stereotype.Component

interface AddImageUseCase {
    fun addImage(command: AddImageCommand)

    data class AddImageCommand(
        val chatId: Long,
        val fileId: String,
        val originalFilename: String,
    )
}

@Component
class AddImageUseCaseImpl(
    private val telegramFilePort: TelegramFilePort,
    private val telegramMessagePort: TelegramMessagePort,
    private val getBotDataPort: GetBotDataPort,
    private val saveStickerDataUseCase: SaveStickerDataUseCase,
) : AddImageUseCase {
    override fun addImage(command: AddImageUseCase.AddImageCommand) {
        val botData = getBotDataPort.getByChatId(command.chatId)
        val file = telegramFilePort.getFileContent(command.fileId)
        botData.getAsCreateStickerData()
            .apply {
                image = file
                status = StickerStatus.SOURCE_FILE_ADDED
                originalFilename = command.originalFilename
            }
        saveStickerDataUseCase.save(botData)
        telegramMessagePort.sendMessageByCode(command.chatId, "command.crt-meme.image-added.response")
    }
}
