package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.swap

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.domain.TelegramUpdateMessage
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.AddImageUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.AddImageUseCase.AddImageCommand
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import org.springframework.stereotype.Component

@Component
class SourceImageHandler(
    private val findBotDataPort: FindBotDataPort,
    private val addSourceImageUseCase: AddImageUseCase,
    private val telegramMessagePort: TelegramMessagePort,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        update
            .let { AddImageCommand(it.chatId, it.document!!.fileId(), it.document.fileName()) }
            .also { addSourceImageUseCase.addImage(it) }
        telegramMessagePort.sendMessageByCode(update.chatId, "command.swp-face.source.response")
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.takeIf { it.document != null }
            ?.let { findBotDataPort.findByChatId(update.chatId)?.isFaceSwapDataInit() == true }
            ?: false
}
