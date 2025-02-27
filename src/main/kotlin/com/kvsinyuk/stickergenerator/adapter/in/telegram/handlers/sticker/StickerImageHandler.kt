package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.sticker

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.domain.TelegramUpdateMessage
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.AddImageUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.AddImageUseCase.AddImageCommand
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.stereotype.Component

@Component
class StickerImageHandler(
    private val findBotDataPort: FindBotDataPort,
    @Qualifier("addImageWithResizeUseCaseImpl")
    private val addImageUseCase: AddImageUseCase,
    private val telegramMessagePort: TelegramMessagePort,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        val fileId =
            update.document?.fileId() ?: update.sticker!!.fileId()
        update
            .let { AddImageCommand(it.chatId, fileId) }
            .also { addImageUseCase.addImage(it) }
        telegramMessagePort.sendMessageByCode(update.chatId, "command.crt-sticker.image-added.response")
    }

    override fun canApply(update: TelegramUpdateMessage) =
        (update.document != null || update.sticker != null) &&
            findBotDataPort.findByChatId(update.chatId)?.isStickerData() == true
}
