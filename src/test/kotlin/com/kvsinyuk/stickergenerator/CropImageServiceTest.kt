package com.kvsinyuk.stickergenerator

import com.kvsinyuk.stickergenerator.applicaiton.service.CropImageService
import com.kvsinyuk.stickergenerator.applicaiton.service.PadImageService
import com.kvsinyuk.stickergenerator.applicaiton.utils.getBufferedImage
import com.kvsinyuk.stickergenerator.domain.BotData
import com.kvsinyuk.stickergenerator.domain.sticker.CreateStickerData
import com.kvsinyuk.stickergenerator.domain.sticker.StickerStatus
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
		val botData = BotData(
			1,
			CreateStickerData(StickerStatus.INIT, imageUrl.file, "", "")
				.apply { image = imageUrl.readBytes() }
		)

		// when
		val croppedImage = cropImageService.cropImage(botData.getAsCreateStickerData().image!!.getBufferedImage())

		// then
		assert(croppedImage.height == 228)
		assert(croppedImage.width == 427)
	}

	@Test
	fun cropImageWithPaddingTest() {
		// given
		val imageUrl = javaClass.getResource(examplePath)
		val botData = BotData(
			1,
			CreateStickerData(StickerStatus.INIT, imageUrl.file, "test text", "")
				.apply { image = imageUrl.readBytes() }
		)

		// when
		val croppedImage = cropImageService.cropImage(botData.getAsCreateStickerData().image!!.getBufferedImage())
		val paddedImage = padImageService.addPaddingIfNecessary(croppedImage, true)

		// then
		assert(croppedImage.width == paddedImage.width)
		assert(croppedImage.height < paddedImage.height)
	}

}
