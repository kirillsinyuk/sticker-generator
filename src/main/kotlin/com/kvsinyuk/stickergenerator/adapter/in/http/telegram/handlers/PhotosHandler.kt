package com.kvsinyuk.stickergenerator.adapter.`in`.http.telegram.handlers

import com.kvsinyuk.stickergenerator.applicaiton.port.MessageSourcePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.TelegramMessagePort
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import org.springframework.stereotype.Component

@Component
class PhotosHandler(
    private val telegramMessagePort: TelegramMessagePort,
    private val messagePort: MessageSourcePort
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        val responseMsg = messagePort.getMessage("command.photos.response")
        telegramMessagePort.sendMessage(update.chatId, responseMsg)
    }

    override fun canApply(update: TelegramUpdateMessage) =
        !update.photos.isNullOrEmpty()
}
