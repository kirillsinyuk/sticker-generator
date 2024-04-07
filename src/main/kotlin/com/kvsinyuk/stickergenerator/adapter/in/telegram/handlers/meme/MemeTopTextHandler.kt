package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.meme

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.meme.AddMemeTopTextUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.meme.AddMemeTopTextUseCase.AddTopTextCommand
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import org.springframework.stereotype.Component

@Component
class MemeTopTextHandler(
    private val findBotDataPort: FindBotDataPort,
    private val addStickerTopTextUseCase: AddMemeTopTextUseCase,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        addStickerTopTextUseCase.addTopText(AddTopTextCommand(update.chatId, update.message!!))
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.takeIf { !it.message.isNullOrBlank() }
            ?.let { findBotDataPort.findByChatId(update.chatId)?.isStickerDataWithSourceFile() == true }
            ?: false
}
