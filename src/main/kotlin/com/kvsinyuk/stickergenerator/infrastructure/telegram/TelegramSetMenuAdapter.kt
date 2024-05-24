package com.kvsinyuk.stickergenerator.infrastructure.telegram

import com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram.TelegramSetMenuPort
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.model.BotCommand
import com.pengrad.telegrambot.request.SetMyCommands
import org.springframework.stereotype.Component
import com.kvsinyuk.stickergenerator.applicaiton.domain.BotCommand as Command

@Component
class TelegramSetMenuAdapter(
    private val bot: TelegramBot,
) : TelegramSetMenuPort {
    override fun setMenu(
        chatId: Long,
        commands: Set<Command>,
    ) {
        val request =
            commands
                .map { BotCommand(it.command, it.description) }
                .let { SetMyCommands(*it.toTypedArray()) }
        bot.execute(request)
    }
}
