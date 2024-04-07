package com.kvsinyuk.stickergenerator.domain.faceswap

import com.kvsinyuk.stickergenerator.domain.CommandData
import org.springframework.data.annotation.TypeAlias

@TypeAlias("face_swap")
data class FaceSwapData(
    var status: FaceSwapStatus = FaceSwapStatus.INIT,
) : CommandData() {
    lateinit var sourceImage: Image
    lateinit var targetImage: Image

    override fun isFaceSwapData() = true

    fun addImage(
        file: ByteArray,
        fileName: String,
    ) {
        when (status) {
            FaceSwapStatus.INIT -> {
                sourceImage = Image(file, fileName)
                status = FaceSwapStatus.SOURCE_FILE_ADDED
            }

            FaceSwapStatus.SOURCE_FILE_ADDED -> {
                targetImage = Image(file, fileName)
                status = FaceSwapStatus.TARGET_FILE_ADDED
            }
            else -> null
        }
    }
}
