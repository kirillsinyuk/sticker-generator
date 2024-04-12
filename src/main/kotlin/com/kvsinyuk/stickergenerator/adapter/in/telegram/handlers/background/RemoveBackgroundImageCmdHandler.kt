package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.background

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.RemoveBackgroundUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.RemoveBackgroundUseCase.RemoveBackgroundCommand
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.DeleteBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.applicaiton.utils.mapToByteArray
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import org.springframework.stereotype.Component

@Component
class RemoveBackgroundImageCmdHandler(
    private val findBotDataPort: FindBotDataPort,
    private val removeBackgroundUseCase: RemoveBackgroundUseCase,
    private val deleteBotDataPort: DeleteBotDataPort,
    private val telegramMessagePort: TelegramMessagePort,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        val document = update.document!!
        val resultImage = removeBackgroundUseCase.removeBackground(RemoveBackgroundCommand(update.chatId, document))
        deleteBotDataPort.delete(update.chatId)
        telegramMessagePort.sendDocument(update.chatId, resultImage.mapToByteArray(), document.fileName())
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.document != null &&
            findBotDataPort.findByChatId(update.chatId)?.isRemoveBackgroundData() == true
}
