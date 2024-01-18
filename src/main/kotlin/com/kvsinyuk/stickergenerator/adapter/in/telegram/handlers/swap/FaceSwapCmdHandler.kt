package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.swap

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.SaveStickerDataUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.domain.BotCommand
import com.kvsinyuk.stickergenerator.domain.BotData
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import com.kvsinyuk.stickergenerator.domain.faceswap.FaceSwapData
import org.springframework.stereotype.Component

@Component
class FaceSwapCmdHandler(
    private val telegramMessagePort: TelegramMessagePort,
    private val saveStickerDataUseCase: SaveStickerDataUseCase,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        saveStickerDataUseCase.save(BotData(update.chatId, FaceSwapData()))
        telegramMessagePort.sendMessageByCode(update.chatId, "command.face-swap.response")
    }

    override fun canApply(update: TelegramUpdateMessage) = update.message == BotCommand.FACE_SWAP.command
}
