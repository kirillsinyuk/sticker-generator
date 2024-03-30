package com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.sticker

import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.CreateStickerUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.DeleteBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.GetBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.applicaiton.utils.mapToByteArray
import org.springframework.stereotype.Component

interface AddBottomTextUseCase {
    fun addBottomText(command: AddBottomTextCommand)

    data class AddBottomTextCommand(
        val chatId: Long,
        val message: String,
    )
}

@Component
class AddBottomTextUseCaseImpl(
    private val telegramMessagePort: TelegramMessagePort,
    private val getBotDataPort: GetBotDataPort,
    private val createStickerUseCase: CreateStickerUseCase,
    private val deleteBotDataPort: DeleteBotDataPort,
) : AddBottomTextUseCase {
    override fun addBottomText(command: AddBottomTextUseCase.AddBottomTextCommand) {
        val stickerData = getBotDataPort.getByChatId(command.chatId)
        val createStickerData =
            stickerData.getAsCreateStickerData()
                .apply { bottomText = command.message.takeIf { it != "*" } ?: "" }

        val stickerFile =
            stickerData
                .let { createStickerUseCase.createSticker(it) }
                .mapToByteArray()

        telegramMessagePort.sendDocument(command.chatId, stickerFile, createStickerData.originalFilename)
            .also { deleteBotDataPort.delete(command.chatId) }
    }
}
