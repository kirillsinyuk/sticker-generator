package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.background

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.RemoveBackgroundUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.DeleteBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramFilePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.applicaiton.utils.getBufferedImage
import com.kvsinyuk.stickergenerator.applicaiton.utils.mapToByteArray
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import org.springframework.stereotype.Component

@Component
class RemoveBackgroundImageCmdHandler(
    private val telegramMessagePort: TelegramMessagePort,
    private val telegramFilePort: TelegramFilePort,
    private val findBotDataPort: FindBotDataPort,
    private val deleteBotDataPort: DeleteBotDataPort,
    private val removeBackgroundUseCase: RemoveBackgroundUseCase
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        val cleanImage = telegramFilePort.getFileContent(update.document!!.fileId())
            .let { removeBackgroundUseCase.removeBackground(it.getBufferedImage(), update.document.fileName()) }
            .also { deleteBotDataPort.delete(update.chatId) }

        telegramMessagePort.sendDocument(update.chatId, cleanImage.mapToByteArray(), update.document.fileName())
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.document != null
                && findBotDataPort.findByChatId(update.chatId)?.commandData
                    ?.isStickerData() == true
}
