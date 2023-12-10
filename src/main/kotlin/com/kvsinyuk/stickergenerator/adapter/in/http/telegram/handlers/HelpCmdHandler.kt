package com.kvsinyuk.stickergenerator.adapter.`in`.http.telegram.handlers

import com.kvsinyuk.stickergenerator.applicaiton.port.MessageSourcePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.TelegramMessagePort
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import com.kvsinyuk.stickergenerator.utils.Commands.HELP_CMD
import org.springframework.stereotype.Component

@Component
class HelpCmdHandler(
    private val telegramMessagePort: TelegramMessagePort,
    private val messagePort: MessageSourcePort
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        val responseMsg = messagePort.getMessage("command.help.response")
        telegramMessagePort.sendMessage(update.chatId, responseMsg)
    }

    override fun canApply(update: TelegramUpdateMessage): Boolean =
        update.message == HELP_CMD
}
