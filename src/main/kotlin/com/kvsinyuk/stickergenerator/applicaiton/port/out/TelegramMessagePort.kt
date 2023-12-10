package com.kvsinyuk.stickergenerator.applicaiton.port.out

interface TelegramMessagePort {
    fun sendMessage(chatId: Long, msg: String)
}
