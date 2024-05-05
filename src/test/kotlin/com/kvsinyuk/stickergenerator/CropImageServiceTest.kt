package com.kvsinyuk.stickergenerator

import com.kvsinyuk.stickergenerator.applicaiton.service.CropImageService
import com.kvsinyuk.stickergenerator.applicaiton.utils.toBufferedImage
import com.kvsinyuk.stickergenerator.domain.BotData
import com.kvsinyuk.stickergenerator.domain.Image
import com.kvsinyuk.stickergenerator.domain.command.StickerData
import com.kvsinyuk.stickergenerator.domain.command.StickerStatus
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
class CropImageServiceTest {
    @Autowired
    private lateinit var cropImageService: CropImageService

    private val examplePath = "/images/carImage.png"

    @Test
    fun `should crop image`() {
        // given
        val resourceImage =
            javaClass.getResource(examplePath)
                .let { Image(it.readBytes(), it.file) }
        val botData =
            BotData(
                1,
                StickerData(StickerStatus.INIT, "", "")
                    .apply { image = resourceImage },
            )

        // when
        val croppedImage = cropImageService.cropImage(botData.getAsStickerData().image.image.toBufferedImage())

        // then
        assert(croppedImage.height < resourceImage.image.toBufferedImage().height)
        assert(croppedImage.width < resourceImage.image.toBufferedImage().width)
        assert(croppedImage.height == 228)
        assert(croppedImage.width == 427)
    }
}
