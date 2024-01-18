package com.kvsinyuk.stickergenerator.applicaiton.exception

class DataNotFoundException(chatId: Long) : RuntimeException("BotData not found for chatId $chatId")
