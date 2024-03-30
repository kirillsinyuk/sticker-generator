package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.background

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.RemoveBackgroundUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.RemoveBackgroundUseCase.RemoveBackgroundCommand
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import org.springframework.stereotype.Component

@Component
class RemoveBackgroundImageCmdHandler(
    private val findBotDataPort: FindBotDataPort,
    private val removeBackgroundUseCase: RemoveBackgroundUseCase,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        val document = update.document!!
        removeBackgroundUseCase.removeBackground(
            RemoveBackgroundCommand(update.chatId, document.fileId(), document.fileName()),
        )
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.document != null &&
            findBotDataPort.findByChatId(update.chatId)?.commandData?.isRemoveBackgroundData() == true
}
