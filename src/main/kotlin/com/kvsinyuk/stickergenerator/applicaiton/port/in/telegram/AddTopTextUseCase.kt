package com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram

import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.GetBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.SaveBotDataPort
import com.kvsinyuk.stickergenerator.domain.BotData
import org.springframework.stereotype.Component

interface AddTopTextUseCase {
    fun addTopText(command: AddTopTextCommand): BotData

    data class AddTopTextCommand(
        val chatId: Long,
        val message: String,
    )
}

@Component
class AddTopTextUseCaseImpl(
    private val getBotDataPort: GetBotDataPort,
    private val saveBotDataPort: SaveBotDataPort,
) : AddTopTextUseCase {
    override fun addTopText(command: AddTopTextUseCase.AddTopTextCommand) =
        getBotDataPort.getByChatId(command.chatId)
            .addText(command.message, true)
            .let { saveBotDataPort.save(it) }
}
