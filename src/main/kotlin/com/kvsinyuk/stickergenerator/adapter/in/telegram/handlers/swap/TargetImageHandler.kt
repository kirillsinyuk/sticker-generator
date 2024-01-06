package com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.swap

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.applicaiton.port.MessageSourcePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.DeleteBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo.GetBotDataPort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramFilePort
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.kvsinyuk.stickergenerator.domain.TelegramUpdateMessage
import com.kvsinyuk.stickergenerator.domain.faceswap.FaceSwapStatus
import org.springframework.stereotype.Component

@Component
class TargetImageHandler(
    private val telegramMessagePort: TelegramMessagePort,
    private val deleteBotDataPort: DeleteBotDataPort,
    private val getBotDataPort: GetBotDataPort,
    private val telegramFilePort: TelegramFilePort,
    private val messagePort: MessageSourcePort
) : TelegramUpdateHandler {

    override fun process(update: TelegramUpdateMessage) {
        val botData = getBotDataPort.getByChatId(update.chatId)
        val file = telegramFilePort.getFileContent(update.document!!.fileId())
        botData.getAsFaceSwapData()
            .apply {
                targetImage = file
                originalFilename = update.document.fileName()
            }

        deleteBotDataPort.delete(update.chatId)
        val responseMsg = messagePort.getMessage("command.face-swap.source.response")
        telegramMessagePort.sendMessage(update.chatId, responseMsg)
    }

    override fun canApply(update: TelegramUpdateMessage): Boolean {
        if (update.document != null) {
            val botData = getBotDataPort.getByChatId(update.chatId)
            return botData.commandData.isFaceSwapData()
                    && botData.getAsFaceSwapData().status == FaceSwapStatus.INIT
        }
        return false
    }
}
