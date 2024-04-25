package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.meme

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.AddTopTextUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.AddTopTextUseCase.AddTopTextCommand
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import org.springframework.stereotype.Component

@Component
class MemeTopTextHandler(
    private val findBotDataPort: FindBotDataPort,
    private val addTopTextUseCase: AddTopTextUseCase,
    private val telegramMessagePort: TelegramMessagePort,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        addTopTextUseCase.addTopText(AddTopTextCommand(update.chatId, update.message!!))
        telegramMessagePort.sendMessageByCode(update.chatId, "command.crt-meme.top-text-added.response")
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.takeIf { !it.message.isNullOrBlank() }
            ?.let { findBotDataPort.findByChatId(update.chatId)?.isMemeDataWithSourceFile() == true }
            ?: false
}
