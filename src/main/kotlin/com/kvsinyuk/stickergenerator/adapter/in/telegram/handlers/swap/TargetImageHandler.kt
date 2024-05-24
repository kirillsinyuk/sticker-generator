package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.swap

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.domain.TelegramUpdateMessage
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.AddImageUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.AddImageUseCase.AddImageCommand
import com.kvsinyuk.stickergenerator.applicaiton.port.out.http.FaceSwapPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.DeleteBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import org.springframework.stereotype.Component

@Component
class TargetImageHandler(
    private val findBotDataPort: FindBotDataPort,
    private val addTargetImageUseCase: AddImageUseCase,
    private val faceSwapPort: FaceSwapPort,
    private val deleteBotDataPort: DeleteBotDataPort,
    private val telegramMessagePort: TelegramMessagePort,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        val botData =
            update
                .let { AddImageCommand(it.chatId, it.document!!.fileId(), it.document.fileName()) }
                .let { addTargetImageUseCase.addImage(it) }
                .getAsFaceSwapData()

        faceSwapPort.swapFace(update.chatId, botData.faceImage, botData.targetImage)
            .also { telegramMessagePort.sendDocument(update.chatId, it, botData.targetImage.fileName) }
            .also { deleteBotDataPort.delete(update.chatId) }
    }

    override fun canApply(update: TelegramUpdateMessage) =
        update.takeIf { it.document != null }
            ?.let { findBotDataPort.findByChatId(update.chatId)?.isFaceSwapDataWithSourceFile() == true }
            ?: false
}
