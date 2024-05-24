package com.kvsinyuk.stickergenerator.applicaiton.port.out.http

import com.kvsinyuk.stickergenerator.applicaiton.domain.Image

interface FaceSwapPort {
    fun swapFace(
        chatId: Long,
        sourceImage: Image,
        targetImage: Image,
    ): ByteArray
}
