package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.swap

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.swap.AddTargetImageUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.telegram.swap.AddTargetImageUseCase.AddTargetImageCommand
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import com.kvsinyuk.stickergenerator.domain.faceswap.FaceSwapStatus
import org.springframework.stereotype.Component

@Component
class TargetImageHandler(
    private val findBotDataPort: FindBotDataPort,
    private val addTargetImageUseCase: AddTargetImageUseCase,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        addTargetImageUseCase.addImage(
            AddTargetImageCommand(
                update.chatId,
                update.document!!.fileId(),
                update.document.fileName(),
            ),
        )
    }

    override fun canApply(update: TelegramUpdateMessage): Boolean {
        if (update.document != null) {
            val botData = findBotDataPort.findByChatId(update.chatId)
            return botData?.commandData?.isFaceSwapData() == true &&
                botData.getAsFaceSwapData().status == FaceSwapStatus.SOURCE_FILE_ADDED
        }
        return false
    }
}
