package com.kvsinyuk.stickergenerator.adapter.`in`.http

import com.kvsinyuk.stickergenerator.applicaiton.port.out.http.RemoveBackgroundPort
import com.kvsinyuk.stickergenerator.applicaiton.utils.toBufferedImage
import com.kvsinyuk.stickergenerator.applicaiton.utils.toByteArray
import mu.KLogging
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/background-removals")
class RemoveBackgroundController(
    private val removeBackgroundPort: RemoveBackgroundPort,
) {
    @PostMapping(produces = [MediaType.IMAGE_PNG_VALUE])
    fun removeBackground(
        @RequestParam("file") file: MultipartFile,
    ): ByteArray {
        logger.info { "Removing background from image ${file.originalFilename}" }
        return removeBackgroundPort
            .removeBackground(file.bytes.toBufferedImage(), file.originalFilename ?: "result.png")
            .toByteArray()
    }

    companion object : KLogging()
}
