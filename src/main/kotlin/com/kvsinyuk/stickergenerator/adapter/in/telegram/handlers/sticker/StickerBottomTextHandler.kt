package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.sticker

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.domain.TelegramUpdateMessage
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.AddBottomTextUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.AddBottomTextUseCase.AddBottomTextCommand
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.DeleteBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import org.springframework.stereotype.Component

@Component
class StickerBottomTextHandler(
    private val findBotDataPort: FindBotDataPort,
    private val addBottomTextUseCase: AddBottomTextUseCase,
    private val telegramMessagePort: TelegramMessagePort,
    private val deleteBotDataPort: DeleteBotDataPort,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        val image = addBottomTextUseCase.addBottomText(AddBottomTextCommand(update.chatId, update.message!!))
            .commandData
            .getSourceImage()
        telegramMessagePort.sendDocument(update.chatId, image.image, image.fileName)
            .also { deleteBotDataPort.delete(update.chatId) }
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.takeIf { !it.message.isNullOrBlank() }
            ?.let { findBotDataPort.findByChatId(it.chatId)?.isStickerDataWithTopText() == true }
            ?: false
}
