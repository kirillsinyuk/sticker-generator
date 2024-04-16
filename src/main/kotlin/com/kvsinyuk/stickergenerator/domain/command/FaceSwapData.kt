package com.kvsinyuk.stickergenerator.domain.command

import com.kvsinyuk.stickergenerator.domain.Image
import org.springframework.data.annotation.TypeAlias

@TypeAlias("face_swap")
data class FaceSwapData(
    var status: FaceSwapStatus = FaceSwapStatus.INIT,
) : CommandData() {
    lateinit var faceImage: Image
    lateinit var targetImage: Image

    override fun isFaceSwapData() = true

    override fun getSourceImage() = faceImage

    fun addImage(
        file: ByteArray,
        fileName: String,
    ) {
        when (status) {
            FaceSwapStatus.INIT -> {
                faceImage = Image(file, fileName)
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

enum class FaceSwapStatus {
    INIT,
    SOURCE_FILE_ADDED,
    TARGET_FILE_ADDED,
}
