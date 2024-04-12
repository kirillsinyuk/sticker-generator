package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.meme

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.AddBottomTextUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.AddBottomTextUseCase.AddBottomTextCommand
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.DeleteBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import org.springframework.stereotype.Component

@Component
class MemeBottomTextHandler(
    private val findBotDataPort: FindBotDataPort,
    private val telegramMessagePort: TelegramMessagePort,
    private val deleteBotDataPort: DeleteBotDataPort,
    private val addMemeBottomTextUseCase: AddBottomTextUseCase,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        val resultImage =
            addMemeBottomTextUseCase.addBottomText(AddBottomTextCommand(update.chatId, update.message!!))
                .commandData.getSourceImage()

        telegramMessagePort.sendDocument(update.chatId, resultImage.image, resultImage.fileName)
            .also { deleteBotDataPort.delete(update.chatId) }
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.takeIf { !it.message.isNullOrBlank() }
            ?.let { findBotDataPort.findByChatId(it.chatId)?.isStickerDataWithTopText() == true }
            ?: false
}
