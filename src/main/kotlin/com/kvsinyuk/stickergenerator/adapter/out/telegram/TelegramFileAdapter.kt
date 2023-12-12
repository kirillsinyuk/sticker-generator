package com.kvsinyuk.stickergenerator.adapter.out.telegram

import com.kvsinyuk.stickergenerator.applicaiton.port.out.TelegramFilePort
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.model.File
import org.springframework.stereotype.Component

@Component
class TelegramFileAdapter(
    private val bot: TelegramBot
) : TelegramFilePort {

    override fun getFileContent(file: File): ByteArray {
        return bot.getFileContent(file)
    }
}
