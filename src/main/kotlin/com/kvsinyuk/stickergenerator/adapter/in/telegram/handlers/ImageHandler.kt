package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers

import com.kvsinyuk.stickergenerator.applicaiton.port.MessageSourcePort
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.SaveStickerDataUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindStickerDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramFilePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.domain.Status
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import org.springframework.stereotype.Component

@Component
class ImageHandler(
    private val telegramFilePort: TelegramFilePort,
    private val telegramMessagePort: TelegramMessagePort,
    private val findStickerDataPort: FindStickerDataPort,
    private val saveStickerDataUseCase: SaveStickerDataUseCase,
    private val messagePort: MessageSourcePort
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        val file = telegramFilePort.getFileContent(update.document!!.fileId())
        findStickerDataPort.findByChatId(update.chatId)
            ?.apply {
                status = Status.MAKE_STICKER_FILE_ADDED
                image = file
                originalFilename = update.document.fileName()
            }?.also { saveStickerDataUseCase.save(it) }
        telegramMessagePort.sendMessage(
            update.chatId,
            messagePort.getMessage("command.mk-sticker.image-added.response")
        )
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.document != null
                && findStickerDataPort.findByChatId(update.chatId)?.status == Status.MAKE_STICKER
}
