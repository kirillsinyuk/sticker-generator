package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.swap

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.MessageSourcePort
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.SaveStickerDataUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.domain.BotData
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import com.kvsinyuk.stickergenerator.domain.faceswap.FaceSwapData
import com.kvsinyuk.stickergenerator.utils.Commands.FACE_SWAP_CMD
import org.springframework.stereotype.Component

@Component
class FaceSwapCmdHandler(
    private val telegramMessagePort: TelegramMessagePort,
    private val saveStickerDataUseCase: SaveStickerDataUseCase,
    private val messagePort: MessageSourcePort
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        saveStickerDataUseCase.save(BotData(update.chatId, FaceSwapData()))
        val responseMsg = messagePort.getMessage("command.face-swap.response")
        telegramMessagePort.sendMessage(update.chatId, responseMsg)
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.message == FACE_SWAP_CMD
}
