package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.swap

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.SaveStickerDataUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.FindBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.GetBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramFilePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import com.kvsinyuk.stickergenerator.domain.faceswap.FaceSwapStatus
import com.kvsinyuk.stickergenerator.domain.faceswap.Image
import org.springframework.stereotype.Component

@Component
class SourceImageHandler(
    private val telegramMessagePort: TelegramMessagePort,
    private val saveStickerDataUseCase: SaveStickerDataUseCase,
    private val getBotDataPort: GetBotDataPort,
    private val findBotDataPort: FindBotDataPort,
    private val telegramFilePort: TelegramFilePort,
) : TelegramUpdateHandler {
    override fun process(update: TelegramUpdateMessage) {
        val botData = getBotDataPort.getByChatId(update.chatId)
        val file = telegramFilePort.getFileContent(update.document!!.fileId())
        botData.getAsFaceSwapData()
            .apply {
                sourceImage = Image(file, update.document.fileName())
                status = FaceSwapStatus.SOURCE_FILE_ADDED
            }
        saveStickerDataUseCase.save(botData)

        telegramMessagePort.sendMessageByCode(update.chatId, "command.face-swap.source.response")
    }

    override fun canApply(update: TelegramUpdateMessage): Boolean {
        if (update.document != null) {
            val botData = findBotDataPort.findByChatId(update.chatId)
            return botData?.commandData?.isFaceSwapData() == true &&
                botData.getAsFaceSwapData().status == FaceSwapStatus.INIT
        }
        return false
    }
}
