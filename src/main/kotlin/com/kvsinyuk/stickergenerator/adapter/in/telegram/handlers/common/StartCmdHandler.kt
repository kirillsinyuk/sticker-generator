package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.common

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.domain.BotCommand
import com.kvsinyuk.stickergenerator.applicaiton.domain.MENU_COMMANDS
import com.kvsinyuk.stickergenerator.applicaiton.domain.TelegramUpdateMessage
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.DeleteBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramSetMenuPort
import org.springframework.stereotype.Component

@Component
class StartCmdHandler(
    private val setMenuPort: TelegramSetMenuPort,
    private val telegramMessagePort: TelegramMessagePort,
    private val deleteBotDataPort: DeleteBotDataPort,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        deleteBotDataPort.delete(update.chatId)

        setMenuPort.setMenu(update.chatId, MENU_COMMANDS)
        telegramMessagePort.sendMessageByCode(update.chatId, "command.start.response")
    }

    override fun canApply(update: TelegramUpdateMessage) = update.message == BotCommand.START.command
}
