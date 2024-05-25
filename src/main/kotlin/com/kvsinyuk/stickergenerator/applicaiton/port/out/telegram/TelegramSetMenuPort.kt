package com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram

import com.kvsinyuk.stickergenerator.applicaiton.domain.BotCommand

interface TelegramSetMenuPort {
    fun setMenu(
        chatId: Long,
        commands: Set<BotCommand>,
    )
}
