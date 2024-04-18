package com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram

import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.GetBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.SaveBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramFilePort
import com.kvsinyuk.stickergenerator.applicaiton.service.ResizeImageService
import com.kvsinyuk.stickergenerator.applicaiton.utils.toBufferedImage
import com.kvsinyuk.stickergenerator.applicaiton.utils.toByteArray
import com.kvsinyuk.stickergenerator.domain.BotData
import com.kvsinyuk.stickergenerator.domain.Image
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

abstract class AddImageUseCase(
    private val telegramFilePort: TelegramFilePort,
    private val getBotDataPort: GetBotDataPort,
    private val saveBotDataPort: SaveBotDataPort,
) {
    open fun addImage(command: AddImageCommand): BotData {
        val file = telegramFilePort.getFileContent(command.fileId)
        return getBotDataPort.getByChatId(command.chatId)
            .let { setImage(it, Image(file, command.originalFilename)) }
            .let { saveBotDataPort.save(it) }
    }

    open fun setImage(
        data: BotData,
        image: Image,
    ): BotData {
        return data.setImage(image)
    }

    data class AddImageCommand(
        val chatId: Long,
        val fileId: String,
        val originalFilename: String,
    )
}

@Primary
@Component
class AddImageUseCaseImpl(
    telegramFilePort: TelegramFilePort,
    getBotDataPort: GetBotDataPort,
    saveBotDataPort: SaveBotDataPort,
) : AddImageUseCase(telegramFilePort, getBotDataPort, saveBotDataPort)

@Component
class AddImageWithResizeUseCaseImpl(
    telegramFilePort: TelegramFilePort,
    getBotDataPort: GetBotDataPort,
    saveBotDataPort: SaveBotDataPort,
    private val resizeImageService: ResizeImageService,
) : AddImageUseCase(telegramFilePort, getBotDataPort, saveBotDataPort) {
    override fun setImage(
        data: BotData,
        image: Image,
    ): BotData {
        val resizedImage =
            resizeImageService.resizeBufferedImage(image.image.toBufferedImage())
                .let { image.copy(image = it.toByteArray()) }
        return data.setImage(resizedImage)
    }
}
