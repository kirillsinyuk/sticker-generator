package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.meme

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.meme.AddMemeBottomTextUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.meme.AddMemeBottomTextUseCase.AddBottomTextCommand
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import org.springframework.stereotype.Component

@Component
class MemeBottomTextHandler(
    private val findBotDataPort: FindBotDataPort,
    private val addMemeBottomTextUseCase: AddMemeBottomTextUseCase,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        addMemeBottomTextUseCase.addBottomText(AddBottomTextCommand(update.chatId, update.message!!))
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.takeIf { !it.message.isNullOrBlank() }
            ?.let { findBotDataPort.findByChatId(it.chatId)?.isStickerDataWithTopText() == true }
            ?: false
}
