package com.kvsinyuk.stickergenerator.domain.faceswap

import com.kvsinyuk.stickergenerator.domain.CommandData
import org.springframework.data.annotation.TypeAlias

@TypeAlias("face_swap")
data class FaceSwapData(
    val status: FaceSwapStatus = FaceSwapStatus.INIT,
    var originalFilename: String = "result.png"
): CommandData() {

    var sourceImage: ByteArray? = null
    var targetImage: ByteArray? = null

    override fun isFaceSwapData() = true
}