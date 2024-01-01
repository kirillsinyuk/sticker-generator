package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.sticker

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.CreateStickerUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.DeleteStickerDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindStickerDataPort
import com.kvsinyuk.stickergenerator.domain.Status
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import org.springframework.stereotype.Component

@Component
class BottomTextHandler(
    private val telegramMessagePort: TelegramMessagePort,
    private val findStickerDataPort: FindStickerDataPort,
    private val createStickerUseCase: CreateStickerUseCase,
    private val deleteStickerDataPort: DeleteStickerDataPort
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        val stickerData = findStickerDataPort.findByChatId(update.chatId)!!
            .apply { bottomText = update.message.takeIf { it != "*" } ?: "" }

        val stickerFile = stickerData
            .let { createStickerUseCase.createSticker(it) }
            .also { deleteStickerDataPort.delete(update.chatId) }

        telegramMessagePort.sendDocument(update.chatId, stickerFile, stickerData.originalFilename)
    }

    override fun canApply(update: TelegramUpdateMessage) =
        !update.message.isNullOrBlank()
                && findStickerDataPort.findByChatId(update.chatId)?.status == Status.MAKE_STICKER_TOP_TEXT_ADDED
}
