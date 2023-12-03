package com.kvsinyuk.stickergenerator.applicaiton.port.`in`

interface CreateStickerUseCase {

    fun createSticker(fileName: String?, sourceFile: ByteArray): ByteArray
}