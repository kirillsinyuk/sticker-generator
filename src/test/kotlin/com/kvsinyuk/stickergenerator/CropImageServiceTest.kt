package com.kvsinyuk.stickergenerator

import com.kvsinyuk.stickergenerator.applicaiton.port.CropImageService
import com.kvsinyuk.stickergenerator.applicaiton.utils.mapToBufferedImage
import com.kvsinyuk.stickergenerator.domain.ImageData
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
	fun cropImageTest() {
		// given
		val imageUrl = javaClass.getResource(examplePath)
		val image = ImageData(imageUrl.readBytes(), imageUrl.file)

		// when
		val croppedImage = cropImageService.cropImage(image)
			.mapToBufferedImage()

		// then
		assert(croppedImage.height == 273)
		assert(croppedImage.width == 511)
	}

}
