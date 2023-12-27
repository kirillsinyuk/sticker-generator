package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers

import com.kvsinyuk.stickergenerator.applicaiton.port.MessageSourcePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindStickerDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.SaveStickerDataPort
import com.kvsinyuk.stickergenerator.domain.Status
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import org.springframework.stereotype.Component

@Component
class TopTextHandler(
    private val telegramMessagePort: TelegramMessagePort,
    private val findStickerDataPort: FindStickerDataPort,
    private val saveStickerDataPort: SaveStickerDataPort,
    private val messagePort: MessageSourcePort
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        findStickerDataPort.findByChatId(update.chatId)
            ?.apply {
                topText = update.message.takeIf { it != "*" } ?: ""
                status = Status.MAKE_STICKER_TOP_TEXT_ADDED
            }?.let { saveStickerDataPort.save(it) }
        telegramMessagePort.sendMessage(
            update.chatId,
            messagePort.getMessage("command.mk-sticker.top-text-added.response")
        )
    }

    override fun canApply(update: TelegramUpdateMessage) =
        !update.message.isNullOrBlank()
                && findStickerDataPort.findByChatId(update.chatId)?.status == Status.MAKE_STICKER_FILE_ADDED
}
