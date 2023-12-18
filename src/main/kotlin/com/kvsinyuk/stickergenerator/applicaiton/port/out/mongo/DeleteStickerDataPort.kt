package com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo

interface DeleteStickerDataPort {

    fun delete(chatId: Long)
}