package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers

import com.kvsinyuk.stickergenerator.applicaiton.port.MessageSourcePort
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.SaveImageFileUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import org.springframework.stereotype.Component

@Component
class DocumentHandler(
    private val telegramMessagePort: TelegramMessagePort,
    private val saveImageFileUseCase: SaveImageFileUseCase,
    private val messagePort: MessageSourcePort
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        saveImageFileUseCase.saveFile(update.chatId, update.document!!)
        telegramMessagePort.sendMessage(
            update.chatId,
            messagePort.getMessage("command.file-added.response")
        )
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.document != null
}
