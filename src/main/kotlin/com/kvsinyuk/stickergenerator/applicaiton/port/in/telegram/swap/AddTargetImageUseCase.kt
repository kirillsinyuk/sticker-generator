package com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.swap

import com.kvsinyuk.stickergenerator.applicaiton.port.out.http.FaceSwapPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.DeleteBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.GetBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramFilePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.domain.faceswap.Image
import org.springframework.stereotype.Component

interface AddTargetImageUseCase {
    fun addImage(command: AddTargetImageCommand)

    data class AddTargetImageCommand(
        val chatId: Long,
        val fileId: String,
        val originalFilename: String,
    )
}

@Component
class AddTargetImageUseCaseImpl(
    private val telegramMessagePort: TelegramMessagePort,
    private val deleteBotDataPort: DeleteBotDataPort,
    private val getBotDataPort: GetBotDataPort,
    private val telegramFilePort: TelegramFilePort,
    private val faceSwapPort: FaceSwapPort,
) : AddTargetImageUseCase {
    override fun addImage(command: AddTargetImageUseCase.AddTargetImageCommand) {
        val file = telegramFilePort.getFileContent(command.fileId)
        val faceSwapData =
            getBotDataPort.getByChatId(command.chatId)
                .getAsFaceSwapData()
                .apply { targetImage = Image(file, command.originalFilename) }

        val resultImage = faceSwapPort.swapFace(command.chatId, faceSwapData.sourceImage, faceSwapData.targetImage)
        telegramMessagePort.sendDocument(command.chatId, resultImage, faceSwapData.targetImage.fileName)
            .also { deleteBotDataPort.delete(command.chatId) }
    }
}
