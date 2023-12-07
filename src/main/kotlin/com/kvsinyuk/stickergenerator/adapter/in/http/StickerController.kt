package com.kvsinyuk.stickergenerator.adapter.`in`.http

import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.CreateStickerUseCase
import com.kvsinyuk.stickergenerator.domain.ImageData
import mu.KLogging
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/stickers")
class StickerController(
    private val createStickerUseCase: CreateStickerUseCase
) {

    @PostMapping(produces = [MediaType.IMAGE_PNG_VALUE])
    fun createSticker(@RequestParam("file") file: MultipartFile): ByteArray {
        logger.info { "Received image ${file.originalFilename} with size ${file.size}" }
        val image = ImageData(file.bytes, file.originalFilename!!)
        return createStickerUseCase.createSticker(image)
    }

    companion object: KLogging()
}