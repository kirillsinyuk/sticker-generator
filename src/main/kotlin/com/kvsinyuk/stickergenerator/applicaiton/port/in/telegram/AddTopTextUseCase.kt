package com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram

import com.kvsinyuk.stickergenerator.applicaiton.domain.BotData
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.GetBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.SaveBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.service.AddTextService
import com.kvsinyuk.stickergenerator.applicaiton.service.PadImageService
import com.kvsinyuk.stickergenerator.applicaiton.utils.toBufferedImage
import com.kvsinyuk.stickergenerator.applicaiton.utils.toByteArray
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

abstract class AddTopTextUseCase(
    private val getBotDataPort: GetBotDataPort,
    private val saveBotDataPort: SaveBotDataPort,
) {
    open fun addTopText(command: AddTopTextCommand) =
        getBotDataPort.getByChatId(command.chatId)
            .addText(command.message, true)
            .let { drawTextOnImage(it) }
            .let { saveBotDataPort.save(it) }

    protected abstract fun drawTextOnImage(data: BotData): BotData

    data class AddTopTextCommand(
        val chatId: Long,
        val message: String,
    )
}

@Primary
@Component
class AddTopTextUseCaseImpl(
    getBotDataPort: GetBotDataPort,
    saveBotDataPort: SaveBotDataPort,
    private val addTextService: AddTextService,
) : AddTopTextUseCase(getBotDataPort, saveBotDataPort) {
    override fun drawTextOnImage(data: BotData): BotData {
        val image = data.commandData.getSourceImage()
        val result =
            addTextService.addText(image.image.toBufferedImage(), data.getTopText(), true)
                .toByteArray()
        return data.setImage(image.copy(image = result))
    }
}

@Component
class AddTopTextWithPaddingUseCaseImpl(
    getBotDataPort: GetBotDataPort,
    saveBotDataPort: SaveBotDataPort,
    private val addTextService: AddTextService,
    private val padImageService: PadImageService,
) : AddTopTextUseCase(getBotDataPort, saveBotDataPort) {
    override fun drawTextOnImage(data: BotData): BotData {
        val image = data.commandData.getSourceImage()
        val result =
            padImageService.addPaddingIfNecessary(image.image.toBufferedImage(), data.getTopText().isNotBlank())
                .let { addTextService.addText(it, data.getTopText(), true) }
                .toByteArray()
        return data.setImage(image.copy(image = result))
    }
}
