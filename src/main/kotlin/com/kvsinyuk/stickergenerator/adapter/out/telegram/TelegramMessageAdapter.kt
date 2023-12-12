package com.kvsinyuk.stickergenerator.adapter.out.telegram

import com.kvsinyuk.stickergenerator.applicaiton.port.out.TelegramMessagePort
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.model.request.ParseMode
import com.pengrad.telegrambot.request.SendMessage
import org.springframework.stereotype.Component

@Component
class TelegramMessageAdapter(
    private val bot: TelegramBot
) : TelegramMessagePort {

    override fun sendMessage(chatId: Long, msg: String) {
        mapMessage(chatId, msg)
            .let { bot.execute(it) }
    }

    private fun mapMessage(chatId: Long, msg: String) =
        SendMessage(chatId, msg)
            .also { it.parseMode(ParseMode.HTML) }
}
