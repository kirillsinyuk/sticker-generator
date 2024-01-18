package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.sticker

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.SaveStickerDataUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.domain.BotCommand
import com.kvsinyuk.stickergenerator.domain.BotData
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import com.kvsinyuk.stickergenerator.domain.sticker.CreateStickerData
import org.springframework.stereotype.Component

@Component
class MakeStickerCmdHandler(
    private val telegramMessagePort: TelegramMessagePort,
    private val saveStickerDataUseCase: SaveStickerDataUseCase,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        saveStickerDataUseCase.save(BotData(update.chatId, CreateStickerData()))
        telegramMessagePort.sendMessageByCode(update.chatId, "command.mk-sticker.response")
    }

    override fun canApply(update: TelegramUpdateMessage) = update.message == BotCommand.MAKE_STICKER.command
}
