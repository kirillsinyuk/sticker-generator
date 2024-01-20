package com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo

interface DeleteBotDataPort {
    fun delete(chatId: Long)
}
