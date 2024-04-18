package com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram

import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.GetBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.SaveBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.service.AddTextService
import com.kvsinyuk.stickergenerator.applicaiton.utils.toBufferedImage
import com.kvsinyuk.stickergenerator.applicaiton.utils.toByteArray
import com.kvsinyuk.stickergenerator.domain.BotData
import org.springframework.stereotype.Component

interface AddBottomTextUseCase {
    fun addBottomText(command: AddBottomTextCommand): BotData

    data class AddBottomTextCommand(
        val chatId: Long,
        val message: String,
    )
}

@Component
class AddBottomTextUseCaseImpl(
    private val getBotDataPort: GetBotDataPort,
    private val addTextService: AddTextService,
    private val saveBotDataPort: SaveBotDataPort,
) : AddBottomTextUseCase {
    override fun addBottomText(command: AddBottomTextUseCase.AddBottomTextCommand): BotData {
        return getBotDataPort.getByChatId(command.chatId)
            .addText(command.message, true)
            .let { drawTextOnImage(it) }
            .let { saveBotDataPort.save(it) }
    }

    private fun drawTextOnImage(data: BotData): BotData {
        val image = data.commandData.getSourceImage()
        val result =
            addTextService.addText(image.image.toBufferedImage(), data.getTopText(), false)
                .toByteArray()
        return data.setImage(image.copy(image = result))
    }
}
