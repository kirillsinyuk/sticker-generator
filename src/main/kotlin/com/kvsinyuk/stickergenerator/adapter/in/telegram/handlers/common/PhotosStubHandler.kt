package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.common

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import org.springframework.stereotype.Component

@Component
class PhotosStubHandler(
    private val telegramMessagePort: TelegramMessagePort
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        telegramMessagePort.sendMessageByCode(update.chatId, "command.photos.response")
    }

    override fun canApply(update: TelegramUpdateMessage) =
        !update.photos.isNullOrEmpty()
}
