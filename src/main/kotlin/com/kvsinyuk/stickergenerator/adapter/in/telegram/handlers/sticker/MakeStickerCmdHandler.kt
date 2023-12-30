package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.sticker

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.MessageSourcePort
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.SaveStickerDataUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.domain.BotData
import com.kvsinyuk.stickergenerator.domain.sticker.CreateStickerData
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import com.kvsinyuk.stickergenerator.utils.Commands.MAKE_STICKER_CMD
import org.springframework.stereotype.Component

@Component
class MakeStickerCmdHandler(
    private val telegramMessagePort: TelegramMessagePort,
    private val saveStickerDataUseCase: SaveStickerDataUseCase,
    private val messagePort: MessageSourcePort
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        saveStickerDataUseCase.save(BotData(update.chatId, CreateStickerData()))
        val responseMsg = messagePort.getMessage("command.mk-sticker.response")
        telegramMessagePort.sendMessage(update.chatId, responseMsg)
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.message == MAKE_STICKER_CMD
}
