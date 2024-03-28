package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.sticker

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.sticker.AddTopTextUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.sticker.AddTopTextUseCase.AddTopTextCommand
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import com.kvsinyuk.stickergenerator.domain.sticker.StickerStatus
import org.springframework.stereotype.Component

@Component
class TopTextHandler(
    private val findBotDataPort: FindBotDataPort,
    private val addStickerTopTextUseCase: AddTopTextUseCase,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        addStickerTopTextUseCase.addTopText(AddTopTextCommand(update.chatId, update.message!!))
    }

    override fun canApply(update: TelegramUpdateMessage): Boolean {
        if (!update.message.isNullOrBlank()) {
            val botData = findBotDataPort.findByChatId(update.chatId)
            return botData?.commandData?.isStickerData() == true &&
                botData.getAsCreateStickerData().status == StickerStatus.SOURCE_FILE_ADDED
        }
        return false
    }
}
