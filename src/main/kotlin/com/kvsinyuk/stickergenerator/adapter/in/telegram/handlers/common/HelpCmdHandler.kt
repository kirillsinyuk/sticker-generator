package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.common

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.MessageSourcePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
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
