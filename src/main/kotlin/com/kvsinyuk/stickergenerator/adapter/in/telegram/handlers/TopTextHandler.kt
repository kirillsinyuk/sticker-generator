package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers

import com.kvsinyuk.stickergenerator.applicaiton.port.MessageSourcePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindStickerDataUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.SaveStickerDataUseCase
import com.kvsinyuk.stickergenerator.domain.Status
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import org.springframework.stereotype.Component

@Component
class TopTextHandler(
    private val telegramMessagePort: TelegramMessagePort,
    private val findStickerDataUseCase: FindStickerDataUseCase,
    private val saveStickerDataUseCase: SaveStickerDataUseCase,
    private val messagePort: MessageSourcePort
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        findStickerDataUseCase.findByChatId(update.chatId)
            ?.apply {
                topText = update.message.takeIf { it != "*" } ?: ""
                status = Status.TOP_TEXT_ADDED
            }?.let { saveStickerDataUseCase.save(it) }
        telegramMessagePort.sendMessage(
            update.chatId,
            messagePort.getMessage("command.top-text-added.response")
        )
    }

    override fun canApply(update: TelegramUpdateMessage) =
        !update.message.isNullOrBlank()
                && findStickerDataUseCase.findByChatId(update.chatId)?.status == Status.FILE_ADDED
}
