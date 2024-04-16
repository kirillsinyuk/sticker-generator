package com.kvsinyuk.stickergenerator

import com.kvsinyuk.stickergenerator.applicaiton.service.CropImageService
import com.kvsinyuk.stickergenerator.applicaiton.service.PadImageService
import com.kvsinyuk.stickergenerator.applicaiton.utils.getBufferedImage
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

    @Autowired
    private lateinit var padImageService: PadImageService

    private val examplePath = "/images/carImage.png"

    @Test
    fun cropImageTest() {
        // given
        val imageUrl = javaClass.getResource(examplePath)
        val botData =
            BotData(
                1,
                StickerData(StickerStatus.INIT, "", "")
                    .apply { image = Image(imageUrl.readBytes(), imageUrl.file) },
            )

        // when
        val croppedImage = cropImageService.cropImage(botData.getAsStickerData().image.image.getBufferedImage())

        // then
        assert(croppedImage.height == 228)
        assert(croppedImage.width == 427)
    }

    @Test
    fun cropImageWithPaddingTest() {
        // given
        val imageUrl = javaClass.getResource(examplePath)
        val botData =
            BotData(
                1,
                StickerData(StickerStatus.INIT, "test text", "")
                    .apply { image = Image(imageUrl.readBytes(), imageUrl.file) },
            )

        // when
        val croppedImage = cropImageService.cropImage(botData.getAsStickerData().image.image.getBufferedImage())
        val paddedImage = padImageService.addPaddingIfNecessary(croppedImage, true)

        // then
        assert(croppedImage.width == paddedImage.width)
        assert(croppedImage.height < paddedImage.height)
    }
}
