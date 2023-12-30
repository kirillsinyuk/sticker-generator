package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.sticker

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.MessageSourcePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.SaveBotDataPort
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import com.kvsinyuk.stickergenerator.domain.sticker.CreateStickerData
import com.kvsinyuk.stickergenerator.domain.sticker.StickerStatus
import org.springframework.stereotype.Component

@Component
class TopTextHandler(
    private val telegramMessagePort: TelegramMessagePort,
    private val findBotDataPort: FindBotDataPort,
    private val saveBotDataPort: SaveBotDataPort,
    private val messagePort: MessageSourcePort
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        val botData = findBotDataPort.findByChatId(update.chatId)
        botData!!.getAsCreateStickerData()
            .apply {
                status = StickerStatus.TOP_TEXT_ADDED
                topText = update.message.takeIf { it != "*" } ?: ""
            }
        saveBotDataPort.save(botData)
        telegramMessagePort.sendMessage(
            update.chatId,
            messagePort.getMessage("command.mk-sticker.top-text-added.response")
        )
    }

    override fun canApply(update: TelegramUpdateMessage): Boolean {
        if (!update.message.isNullOrBlank()) {
            val commandData = findBotDataPort.findByChatId(update.chatId)?.commandData
            return commandData?.isStickerData() == true
                    && (commandData as CreateStickerData).status == StickerStatus.SOURCE_FILE_ADDED
        }
        return false
    }
}
