package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.background

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.domain.BotCommand.REMOVE_BACKGROUND
import com.kvsinyuk.stickergenerator.applicaiton.domain.BotData
import com.kvsinyuk.stickergenerator.applicaiton.domain.TelegramUpdateMessage
import com.kvsinyuk.stickergenerator.applicaiton.domain.command.RemoveBackgroundData
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.SaveStickerDataUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import org.springframework.stereotype.Component

@Component
class RemoveBackgroundCmdHandler(
    private val telegramMessagePort: TelegramMessagePort,
    private val saveStickerDataUseCase: SaveStickerDataUseCase,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        saveStickerDataUseCase.save(BotData(update.chatId, RemoveBackgroundData()))
        telegramMessagePort.sendMessageByCode(update.chatId, "command.rm-background.response")
    }

    override fun canApply(update: TelegramUpdateMessage) = update.message == REMOVE_BACKGROUND.command
}
