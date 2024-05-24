package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.swap

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.domain.BotCommand.FACE_SWAP
import com.kvsinyuk.stickergenerator.applicaiton.domain.BotData
import com.kvsinyuk.stickergenerator.applicaiton.domain.TelegramUpdateMessage
import com.kvsinyuk.stickergenerator.applicaiton.domain.command.FaceSwapData
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.SaveStickerDataUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import org.springframework.stereotype.Component

@Component
class FaceSwapCmdHandler(
    private val telegramMessagePort: TelegramMessagePort,
    private val saveStickerDataUseCase: SaveStickerDataUseCase,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        saveStickerDataUseCase.save(BotData(update.chatId, FaceSwapData()))
        telegramMessagePort.sendMessageByCode(update.chatId, "command.swp-face.response")
    }

    override fun canApply(update: TelegramUpdateMessage) = update.message == FACE_SWAP.command
}
