package com.kvsinyuk.stickergenerator.applicaiton.port.out

import com.pengrad.telegrambot.model.File

interface TelegramFilePort {
    fun getFileContent(file: File): ByteArray
}
