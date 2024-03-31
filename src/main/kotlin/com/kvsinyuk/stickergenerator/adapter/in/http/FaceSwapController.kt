package com.kvsinyuk.stickergenerator.adapter.`in`.http

import com.kvsinyuk.stickergenerator.applicaiton.port.out.http.FaceSwapPort
import com.kvsinyuk.stickergenerator.domain.faceswap.Image
import mu.KLogging
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/face-swaps")
class FaceSwapController(
    private val faceSwapPort: FaceSwapPort,
) {
    @PostMapping(produces = [MediaType.IMAGE_PNG_VALUE])
    fun createSticker(
        @RequestParam("sourceFile") sourceFile: MultipartFile,
        @RequestParam("targetFile") targetFile: MultipartFile,
    ): ByteArray {
        logger.info { "Putting face from image ${sourceFile.originalFilename} to ${targetFile.originalFilename}" }
        val sourceImage = Image(sourceFile.bytes, sourceFile.originalFilename ?: "source.png")
        val targetImage = Image(targetFile.bytes, targetFile.originalFilename ?: "target.png")
        return faceSwapPort.swapFace(1, sourceImage, targetImage)
    }

    companion object : KLogging()
}
