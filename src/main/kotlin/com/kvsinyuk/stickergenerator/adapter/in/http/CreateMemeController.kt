package com.kvsinyuk.stickergenerator.adapter.`in`.http

import com.kvsinyuk.stickergenerator.applicaiton.domain.BotData
import com.kvsinyuk.stickergenerator.applicaiton.domain.Image
import com.kvsinyuk.stickergenerator.applicaiton.domain.command.MemeData
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.CreateMemeUseCase
import com.kvsinyuk.stickergenerator.applicaiton.utils.toByteArray
import mu.KLogging
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/memes")
class CreateMemeController(
    private val createMemeUseCase: CreateMemeUseCase,
) {
    @PostMapping(produces = [MediaType.IMAGE_PNG_VALUE])
    fun createMeme(
        @RequestParam("file") file: MultipartFile,
        @RequestParam("topText") topText: String = "",
        @RequestParam("bottomText") bottomText: String = "",
    ): ByteArray {
        logger.info { "Creating a meme from image ${file.originalFilename}" }
        val sourceImage = Image(file.bytes, file.originalFilename ?: "source.png")
        return createMemeUseCase.createMeme(
            BotData(1L, MemeData(topText = topText, bottomText = bottomText).also { it.addImage(sourceImage) }),
        ).toByteArray()
    }

    companion object : KLogging()
}
