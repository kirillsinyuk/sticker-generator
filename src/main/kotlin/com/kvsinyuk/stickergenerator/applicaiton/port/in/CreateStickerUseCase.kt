package com.kvsinyuk.stickergenerator.applicaiton.port.`in`

import com.kvsinyuk.stickergenerator.applicaiton.domain.BotData
import com.kvsinyuk.stickergenerator.applicaiton.service.AddTextService
import com.kvsinyuk.stickergenerator.applicaiton.service.PadImageService
import com.kvsinyuk.stickergenerator.applicaiton.service.ResizeImageService
import com.kvsinyuk.stickergenerator.applicaiton.utils.toBufferedImage
import org.springframework.stereotype.Component
import java.awt.image.BufferedImage

interface CreateStickerUseCase {
    fun createSticker(botData: BotData): BufferedImage
}

@Component
class CreateStickerUseCaseImpl(
    private val resizeImageService: ResizeImageService,
    private val addTextService: AddTextService,
    private val padImageService: PadImageService,
) : CreateStickerUseCase {
    override fun createSticker(botData: BotData): BufferedImage {
        val stickerData = botData.getAsStickerData()
        return resizeImageService.resizeBufferedImage(stickerData.image.image.toBufferedImage())
            .let { padImageService.addPaddingIfNecessary(it, stickerData.topText.isBlank()) }
            .let { addTextService.addText(it, botData.getAsStickerData().topText, true) }
            .let { addTextService.addText(it, botData.getAsStickerData().bottomText, false) }
    }
}
