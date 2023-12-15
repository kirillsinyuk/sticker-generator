package com.kvsinyuk.stickergenerator.applicaiton.port.out

interface TelegramFilePort {
    fun getFileContent(fileId: String): ByteArray
}
