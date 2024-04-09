package com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.sticker

import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.GetBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.SaveBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.domain.sticker.StickerStatus
import org.springframework.stereotype.Component

interface AddTopTextUseCase {
    fun addTopText(command: AddTopTextCommand)

    data class AddTopTextCommand(
        val chatId: Long,
        val message: String,
    )
}

@Component
class AddTopTextUseCaseImpl(
    private val telegramMessagePort: TelegramMessagePort,
    private val getBotDataPort: GetBotDataPort,
    private val saveBotDataPort: SaveBotDataPort,
) : AddTopTextUseCase {
    override fun addTopText(command: AddTopTextUseCase.AddTopTextCommand) {
        val botData = getBotDataPort.getByChatId(command.chatId)
        botData.getAsCreateStickerData()
            .apply {
                status = StickerStatus.TOP_TEXT_ADDED
                topText = command.message.takeIf { it != "*" } ?: ""
            }
        saveBotDataPort.save(botData)
        telegramMessagePort.sendMessageByCode(command.chatId, "command.crt-sticker.top-text-added.response")
    }
}
