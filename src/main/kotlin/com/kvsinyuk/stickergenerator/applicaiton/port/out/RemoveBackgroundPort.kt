package com.kvsinyuk.stickergenerator.applicaiton.port.out

interface RemoveBackgroundPort {

    fun removeBackground(fileName: String?, image: ByteArray): ByteArray
}
