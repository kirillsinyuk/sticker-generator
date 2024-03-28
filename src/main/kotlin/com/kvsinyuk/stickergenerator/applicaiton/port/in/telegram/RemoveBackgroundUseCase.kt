package com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram

import com.kvsinyuk.stickergenerator.applicaiton.port.out.http.RemoveBackgroundPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.DeleteBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramFilePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.applicaiton.utils.getBufferedImage
import com.kvsinyuk.stickergenerator.applicaiton.utils.mapToByteArray
import org.springframework.stereotype.Component

interface RemoveBackgroundUseCase {
    fun removeBackground(command: RemoveBackgroundCommand)

    data class RemoveBackgroundCommand(
        val chatId: Long,
        val fileId: String,
        val originalFilename: String,
    )
}

@Component
class RemoveBackgroundUseCaseImpl(
    private val removeBackgroundPort: RemoveBackgroundPort,
    private val telegramMessagePort: TelegramMessagePort,
    private val telegramFilePort: TelegramFilePort,
    private val deleteBotDataPort: DeleteBotDataPort,
) : RemoveBackgroundUseCase {
    override fun removeBackground(command: RemoveBackgroundUseCase.RemoveBackgroundCommand) {
        val cleanImage =
            telegramFilePort.getFileContent(command.fileId)
                .let { removeBackgroundPort.removeBackground(it.getBufferedImage(), command.originalFilename) }
                .also { deleteBotDataPort.delete(command.chatId) }

        telegramMessagePort.sendDocument(command.chatId, cleanImage.mapToByteArray(), command.originalFilename)
    }
}
