package com.kvsinyuk.stickergenerator.adapter.`in`.http

import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.CreateStickerUseCase
import mu.KLogging
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

    @PostMapping
    fun createSticker(@RequestParam("image") image: MultipartFile): ByteArray {
        logger.info { "Received image ${image.originalFilename} with size ${image.size}" }
        return createStickerUseCase.createSticker(image.originalFilename, image.bytes)
    }

    companion object: KLogging()
}