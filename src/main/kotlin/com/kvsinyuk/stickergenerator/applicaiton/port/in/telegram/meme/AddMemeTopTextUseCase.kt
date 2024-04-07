package com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.meme

import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.GetBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.SaveBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.domain.meme.MemeStatus
import org.springframework.stereotype.Component

interface AddMemeTopTextUseCase {
    fun addTopText(command: AddTopTextCommand)

    data class AddTopTextCommand(
        val chatId: Long,
        val message: String,
    )
}

@Component
class AddMemeTopTextUseCaseImpl(
    private val telegramMessagePort: TelegramMessagePort,
    private val getBotDataPort: GetBotDataPort,
    private val saveBotDataPort: SaveBotDataPort,
) : AddMemeTopTextUseCase {
    override fun addTopText(command: AddMemeTopTextUseCase.AddTopTextCommand) {
        val botData = getBotDataPort.getByChatId(command.chatId)
        botData.getAsCreateMemeData()
            .apply {
                status = MemeStatus.TOP_TEXT_ADDED
                topText = command.message.takeIf { it != "*" } ?: ""
            }
        saveBotDataPort.save(botData)
        telegramMessagePort.sendMessageByCode(command.chatId, "command.mk-sticker.top-text-added.response")
    }
}
