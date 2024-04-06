package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.swap

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.swap.AddSourceImageUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.swap.AddSourceImageUseCase.AddSourceImageCommand
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import org.springframework.stereotype.Component

@Component
class SourceImageHandler(
    private val findBotDataPort: FindBotDataPort,
    private val addSourceImageUseCase: AddSourceImageUseCase,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        update
            .let { AddSourceImageCommand(it.chatId, it.document!!.fileId(), it.document.fileName()) }
            .also { addSourceImageUseCase.addImage(it) }
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.takeIf { it.document != null }
            ?.let { findBotDataPort.findByChatId(update.chatId)?.isFaceSwapDataInit() == true }
            ?: false
}
