package com.kvsinyuk.stickergenerator.applicaiton.impl

import com.kvsinyuk.stickergenerator.applicaiton.service.AddTextService
import com.kvsinyuk.stickergenerator.applicaiton.service.CropImageService
import com.kvsinyuk.stickergenerator.applicaiton.service.PadImageService
import com.kvsinyuk.stickergenerator.applicaiton.service.ResizeImageService
import com.kvsinyuk.stickergenerator.applicaiton.port.`in`.CreateStickerUseCase
import com.kvsinyuk.stickergenerator.applicaiton.port.out.http.RemoveBackgroundPort
import com.kvsinyuk.stickergenerator.applicaiton.utils.mapToByteArray
import com.kvsinyuk.stickergenerator.domain.StickerData
import org.springframework.stereotype.Component

@Component
class CreateStickerUseCaseImpl(
    private val removeBackgroundPort: RemoveBackgroundPort,
    private val cropImageService: CropImageService,
    private val resizeImageService: ResizeImageService,
    private val addTextService: AddTextService,
    private val padImageService: PadImageService
) : CreateStickerUseCase {

    override fun createSticker(stickerData: StickerData) =
        removeBackgroundPort.removeBackground(stickerData)
            .let { cropImageService.cropImage(it) }
            .let { padImageService.addPaddingIfNecessary(it) }
            .let { resizeImageService.resizeBufferedImage(it) }
            .let { addTextService.addText(it, stickerData.topText, true) }
            .let { addTextService.addText(it, stickerData.bottomText, false) }
            .mapToByteArray()
}