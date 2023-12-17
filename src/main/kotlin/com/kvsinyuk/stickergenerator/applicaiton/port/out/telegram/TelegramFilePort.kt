package com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram

interface TelegramFilePort {
    fun getFileContent(fileId: String): ByteArray
}
