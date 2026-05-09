package com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram

interface TelegramMessagePort {
    fun sendMessageByCode(
        chatId: Long,
        msgCode: String,
    )

    fun sendPhoto(
        chatId: Long,
        photo: ByteArray,
        fileName: String,
    )
}
