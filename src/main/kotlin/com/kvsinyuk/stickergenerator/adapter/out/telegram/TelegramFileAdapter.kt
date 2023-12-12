package com.kvsinyuk.stickergenerator.adapter.out.telegram

import com.kvsinyuk.stickergenerator.applicaiton.port.out.TelegramFilePort
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.request.GetFile
import org.springframework.stereotype.Component

@Component
class TelegramFileAdapter(
    private val bot: TelegramBot
) : TelegramFilePort {

    override fun getFileContent(fileId: String): ByteArray {
        return bot.execute(GetFile(fileId))
            .let { bot.getFileContent(it.file()) }
    }
}
