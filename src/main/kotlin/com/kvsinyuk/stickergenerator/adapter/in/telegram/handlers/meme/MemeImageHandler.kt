package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.meme

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.sticker.AddImageUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.sticker.AddImageUseCase.AddImageCommand
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import org.springframework.stereotype.Component

@Component
class MemeImageHandler(
    private val findBotDataPort: FindBotDataPort,
    private val addImageUseCase: AddImageUseCase,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        update
            .let { AddImageCommand(it.chatId, it.document!!.fileId(), it.document.fileName()) }
            .also { addImageUseCase.addImage(it) }
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.document != null &&
            findBotDataPort.findByChatId(update.chatId)?.isMemeData() == true
}
