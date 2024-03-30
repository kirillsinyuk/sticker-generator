package com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.swap

import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.SaveStickerDataUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.GetBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramFilePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.domain.faceswap.FaceSwapStatus
import com.kvsinyuk.stickergenerator.domain.faceswap.Image
import org.springframework.stereotype.Component

interface AddSourceImageUseCase {
    fun addImage(command: AddSourceImageCommand)

    data class AddSourceImageCommand(
        val chatId: Long,
        val fileId: String,
        val originalFilename: String,
    )
}

@Component
class AddSourceUseCaseImpl(
    private val telegramMessagePort: TelegramMessagePort,
    private val saveStickerDataUseCase: SaveStickerDataUseCase,
    private val getBotDataPort: GetBotDataPort,
    private val telegramFilePort: TelegramFilePort,
) : AddSourceImageUseCase {
    override fun addImage(command: AddSourceImageUseCase.AddSourceImageCommand) {
        val botData = getBotDataPort.getByChatId(command.chatId)
        val file = telegramFilePort.getFileContent(command.fileId)
        botData.getAsFaceSwapData()
            .apply {
                sourceImage = Image(file, command.originalFilename)
                status = FaceSwapStatus.SOURCE_FILE_ADDED
            }
        saveStickerDataUseCase.save(botData)

        telegramMessagePort.sendMessageByCode(command.chatId, "command.face-swap.source.response")
    }
}
