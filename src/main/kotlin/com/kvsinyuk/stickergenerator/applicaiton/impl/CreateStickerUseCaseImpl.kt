package com.kvsinyuk.stickergenerator.applicaiton.impl

import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.CreateStickerUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.http.RemoveBackgroundPort
import com.kvsinyuk.stickergenerator.applicaiton.service.AddTextService
import com.kvsinyuk.stickergenerator.applicaiton.service.CropImageService
import com.kvsinyuk.stickergenerator.applicaiton.service.PadImageService
import com.kvsinyuk.stickergenerator.applicaiton.service.ResizeImageService
import com.kvsinyuk.stickergenerator.applicaiton.utils.getBufferedImage
import com.kvsinyuk.stickergenerator.domain.BotData
import org.springframework.stereotype.Component
import java.awt.image.BufferedImage

@Component
class CreateStickerUseCaseImpl(
    private val removeBackgroundPort: RemoveBackgroundPort,
    private val cropImageService: CropImageService,
    private val resizeImageService: ResizeImageService,
    private val addTextService: AddTextService,
    private val padImageService: PadImageService,
) : CreateStickerUseCase {
    override fun createSticker(botData: BotData): BufferedImage {
        val stickerData = botData.getAsCreateStickerData()
        return removeBackgroundPort.removeBackground(stickerData.image!!.getBufferedImage(), stickerData.originalFilename)
            .let { cropImageService.cropImage(it) }
            .let { padImageService.addPaddingIfNecessary(it, stickerData.topText.isBlank()) }
            .let { resizeImageService.resizeBufferedImage(it) }
            .let { addTextService.addText(it, botData.getAsCreateStickerData().topText, true) }
            .let { addTextService.addText(it, botData.getAsCreateStickerData().bottomText, false) }
    }
}
