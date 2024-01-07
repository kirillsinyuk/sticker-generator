package com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram

import com.kvsinyuk.stickergenerator.domain.BotCommand

interface TelegramSetMenuPort {
    fun setMenu(chatId: Long, commands: Set<BotCommand>)
}
