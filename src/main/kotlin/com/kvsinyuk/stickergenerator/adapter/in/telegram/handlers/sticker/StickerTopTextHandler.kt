package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.sticker

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.domain.TelegramUpdateMessage
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.AddTopTextUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.AddTopTextUseCase.AddTopTextCommand
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class StickerTopTextHandler(
    private val findBotDataPort: FindBotDataPort,
    @Qualifier("addTopTextWithPaddingUseCaseImpl")
    private val addTopTextUseCase: AddTopTextUseCase,
    private val telegramMessagePort: TelegramMessagePort,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        addTopTextUseCase.addTopText(AddTopTextCommand(update.chatId, update.message!!))
        telegramMessagePort.sendMessageByCode(update.chatId, "command.crt-sticker.top-text-added.response")
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.takeIf { !it.message.isNullOrBlank() }
            ?.let { findBotDataPort.findByChatId(update.chatId)?.isStickerDataWithSourceFile() == true }
            ?: false
}
