package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.sticker

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.sticker.AddImageUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.sticker.AddImageUseCase.AddImageCommand
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import org.springframework.stereotype.Component

@Component
class ImageHandler(
    private val findBotDataPort: FindBotDataPort,
    private val addImageUseCase: AddImageUseCase,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        addImageUseCase.addImage(
            AddImageCommand(
                update.chatId,
                update.document!!.fileId(),
                update.document.fileName(),
            ),
        )
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.document != null &&
            findBotDataPort.findByChatId(update.chatId)?.commandData?.isStickerData() == true
}
