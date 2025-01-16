package com.kvsinyuk.stickergenerator.applicaiton.port.out.telegram

import com.kvsinyuk.stickergenerator.applicaiton.domain.Image

interface TelegramFilePort {
    fun getFileContent(fileId: String): Image
}
