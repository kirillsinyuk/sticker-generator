package com.kvsinyuk.stickergenerator

import com.kvsinyuk.stickergenerator.applicaiton.port.CropImageService
import com.kvsinyuk.stickergenerator.applicaiton.port.PadImageService
import com.kvsinyuk.stickergenerator.domain.StickerData
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
		val image = StickerData(imageUrl.readBytes(), imageUrl.file, "", "")

		// when
		val croppedImage = cropImageService.cropImage(image)
			.getBufferedImage()

		// then
		assert(croppedImage.height == 228)
		assert(croppedImage.width == 427)
	}

	@Test
	fun cropImageWithPaddingTest() {
		// given
		val imageUrl = javaClass.getResource(examplePath)
		val image = StickerData(imageUrl.readBytes(), imageUrl.file, "test text", "")

		// when
		val croppedImage = cropImageService.cropImage(image)
		val paddedImage = padImageService.addPaddingIfNecessary(croppedImage)

		// then
		val croppedBufferedImage = croppedImage.getBufferedImage()
		assert(croppedBufferedImage.width == paddedImage.width)
		assert(croppedBufferedImage.height < paddedImage.height)
	}

}
