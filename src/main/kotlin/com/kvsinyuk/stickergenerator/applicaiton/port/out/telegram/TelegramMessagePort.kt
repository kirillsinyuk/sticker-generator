package com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram

interface TelegramMessagePort {
    fun sendMessage(chatId: Long, msg: String)

    fun sendDocument(chatId: Long, photo: ByteArray, fileName: String)
}
