package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.swap

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.swap.AddTargetImageUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.swap.AddTargetImageUseCase.AddTargetImageCommand
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import org.springframework.stereotype.Component

@Component
class TargetImageHandler(
    private val findBotDataPort: FindBotDataPort,
    private val addTargetImageUseCase: AddTargetImageUseCase,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        update
            .let { AddTargetImageCommand(it.chatId, it.document!!.fileId(), it.document.fileName()) }
            .also { addTargetImageUseCase.addImage(it) }
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.takeIf { it.document != null }
            ?.let { findBotDataPort.findByChatId(update.chatId)?.isFaceSwapDataWithSourceFile() == true }
            ?: false
}
