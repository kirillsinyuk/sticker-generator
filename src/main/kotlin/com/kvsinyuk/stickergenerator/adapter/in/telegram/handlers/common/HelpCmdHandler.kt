package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.common

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.domain.BotCommand
import com.kvsinyuk.stickergenerator.applicaiton.domain.TelegramUpdateMessage
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import org.springframework.stereotype.Component

@Component
class HelpCmdHandler(
    private val telegramMessagePort: TelegramMessagePort,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        telegramMessagePort.sendMessageByCode(update.chatId, "command.help.response")
    }

    override fun canApply(update: TelegramUpdateMessage) = update.message == BotCommand.HELP.command
}
