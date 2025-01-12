package com.kvsinyuk.stickergenerator.infrastructure.telegram

import com.kvsinyuk.stickergenerator.applicaiton.domain.Image
import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramFilePort
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.request.GetFile
import org.springframework.stereotype.Component

@Component
class TelegramFileAdapter(
    private val bot: TelegramBot,
) : TelegramFilePort {
    override fun getFileContent(fileId: String): Image {
        val file = bot.execute(GetFile(fileId))
        val fileName =
            file.file().filePath()
                .replaceBeforeLast("/", "")
                .replace("/", "")
        return Image(bot.getFileContent(file.file()), fileName)
    }
}
