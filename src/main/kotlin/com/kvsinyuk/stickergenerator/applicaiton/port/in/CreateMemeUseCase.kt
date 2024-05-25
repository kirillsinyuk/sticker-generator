package com.kvsinyuk.stickergenerator.applicaiton.port.`in`

import com.kvsinyuk.stickergenerator.applicaiton.domain.BotData
import com.kvsinyuk.stickergenerator.applicaiton.service.AddTextService
import com.kvsinyuk.stickergenerator.applicaiton.utils.toBufferedImage
import org.springframework.stereotype.Component
import java.awt.image.BufferedImage

interface CreateMemeUseCase {
    fun createMeme(botData: BotData): BufferedImage
}

@Component
class CreateMemeUseCaseImpl(
    private val addTextService: AddTextService,
) : CreateMemeUseCase {
    override fun createMeme(botData: BotData): BufferedImage {
        val memeData = botData.getAsMemeData()
        return addTextService.addText(memeData.image.image.toBufferedImage(), botData.getAsStickerData().topText, true)
            .let { addTextService.addText(it, memeData.bottomText, false) }
    }
}
