package com.kvsinyuk.stickergenerator.adapter.out.telegram

import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramMessagePort
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.model.request.ParseMode
import com.pengrad.telegrambot.request.SendDocument
import com.pengrad.telegrambot.request.SendMessage
import org.springframework.stereotype.Component

@Component
class TelegramMessageAdapter(
    private val bot: TelegramBot
) : TelegramMessagePort {

    private val DEFAULT_TYPE = "image/png"

    override fun sendMessage(chatId: Long, msg: String) {
        mapMessage(chatId, msg)
            .let { bot.execute(it) }
    }

    override fun sendDocument(chatId: Long, document: ByteArray, fileName: String) {
        mapDocument(chatId, document, fileName)
            .let { bot.execute(it) }
    }

    private fun mapMessage(chatId: Long, msg: String) =
        SendMessage(chatId, msg)
            .also { it.parseMode(ParseMode.HTML) }

    private fun mapDocument(chatId: Long, document: ByteArray, fileName: String) =
        SendDocument(chatId, document)
            .contentType(DEFAULT_TYPE)
            .fileName(fileName)
            .also { it.parseMode(ParseMode.HTML) }
}
