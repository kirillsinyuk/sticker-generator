package com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.meme

import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.DeleteBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.GetBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.applicaiton.service.AddTextService
import com.kvsinyuk.stickergenerator.applicaiton.utils.getBufferedImage
import com.kvsinyuk.stickergenerator.applicaiton.utils.mapToByteArray
import org.springframework.stereotype.Component

interface AddMemeBottomTextUseCase {
    fun addBottomText(command: AddBottomTextCommand)

    data class AddBottomTextCommand(
        val chatId: Long,
        val message: String,
    )
}

@Component
class AddMemeBottomTextUseCaseImpl(
    private val telegramMessagePort: TelegramMessagePort,
    private val getBotDataPort: GetBotDataPort,
    private val addTextService: AddTextService,
    private val deleteBotDataPort: DeleteBotDataPort,
) : AddMemeBottomTextUseCase {
    override fun addBottomText(command: AddMemeBottomTextUseCase.AddBottomTextCommand) {
        val stickerData = getBotDataPort.getByChatId(command.chatId)
        val createStickerData =
            stickerData.getAsCreateMemeData()
                .apply { bottomText = command.message.takeIf { it != "*" } ?: "" }

        val stickerFile =
            stickerData.getAsCreateMemeData().image!!.getBufferedImage()
                .let { addTextService.addText(it, createStickerData.topText, true) }
                .let { addTextService.addText(it, createStickerData.bottomText, false) }
                .mapToByteArray()

        telegramMessagePort.sendDocument(command.chatId, stickerFile, createStickerData.originalFilename)
            .also { deleteBotDataPort.delete(command.chatId) }
    }
}
