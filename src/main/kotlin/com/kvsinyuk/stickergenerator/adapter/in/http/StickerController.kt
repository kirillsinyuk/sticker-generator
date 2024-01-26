package com.kvsinyuk.stickergenerator.adapter.`in`.http

import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.CreateStickerUseCase
import com.kvsinyuk.stickergenerator.applicaiton.utils.mapToByteArray
import com.kvsinyuk.stickergenerator.domain.BotData
import com.kvsinyuk.stickergenerator.domain.sticker.CreateStickerData
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
    private val createStickerUseCase: CreateStickerUseCase,
) {
    @PostMapping(produces = [MediaType.IMAGE_PNG_VALUE])
    fun createSticker(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("topText") topText: String = "",
        @RequestParam("bottomText") bottomText: String = "",
    ): ByteArray {
        logger.info { "Creating sticker for image ${file.originalFilename}" }
        val image =
            CreateStickerData(topText = topText, bottomText = bottomText)
                .apply { image = file.bytes }
                .let { BotData(1, it) }
        return createStickerUseCase.createSticker(image)
            .mapToByteArray()
    }

    companion object : KLogging()
}
