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

    fun addImage(image: Image) {
        when (status) {
            FaceSwapStatus.INIT -> {
                faceImage = image
                status = FaceSwapStatus.SOURCE_FILE_ADDED
            }

            FaceSwapStatus.SOURCE_FILE_ADDED -> {
                targetImage = image
                status = FaceSwapStatus.TARGET_FILE_ADDED
            }
            else -> {}
        }
    }
}

enum class FaceSwapStatus {
    INIT,
    SOURCE_FILE_ADDED,
    TARGET_FILE_ADDED,
}
