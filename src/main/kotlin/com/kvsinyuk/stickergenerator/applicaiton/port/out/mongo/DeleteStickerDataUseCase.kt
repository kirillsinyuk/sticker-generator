package com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo

interface DeleteStickerDataUseCase {

    fun delete(chatId: Long)
}