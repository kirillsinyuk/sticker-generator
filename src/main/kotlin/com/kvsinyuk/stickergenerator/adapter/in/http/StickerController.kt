package com.kvsinyuk.stickergenerator.adapter.`in`.http

import com.kvsinyuk.stickergenerator.applicaiton.domain.BotData
import com.kvsinyuk.stickergenerator.applicaiton.domain.Image
import com.kvsinyuk.stickergenerator.applicaiton.domain.command.StickerData
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.CreateStickerUseCase
import com.kvsinyuk.stickergenerator.applicaiton.utils.toByteArray
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
            StickerData(topText = topText, bottomText = bottomText)
                .apply { image = Image(file.bytes, file.originalFilename!!) }
                .let { BotData(1, it) }
        return createStickerUseCase.createSticker(image)
            .toByteArray()
    }

    companion object : KLogging()
}
