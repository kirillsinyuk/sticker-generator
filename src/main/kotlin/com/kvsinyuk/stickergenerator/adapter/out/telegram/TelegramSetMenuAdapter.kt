package com.kvsinyuk.stickergenerator.adapter.out.telegram

import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramSetMenuPort
import com.kvsinyuk.stickergenerator.domain.BotCommand as Command
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.model.BotCommand
import com.pengrad.telegrambot.request.SetMyCommands
import org.springframework.stereotype.Component

@Component
class TelegramSetMenuAdapter(
    private val bot: TelegramBot
) : TelegramSetMenuPort {

    override fun setMenu(chatId: Long, commands: Set<Command>) {
        val request = commands
            .map { BotCommand(it.command, it.description) }
            .let { SetMyCommands(*it.toTypedArray()) }
        bot.execute(request)
    }
}
