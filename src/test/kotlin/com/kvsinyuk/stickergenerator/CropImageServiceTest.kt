package com.kvsinyuk.stickergenerator

import com.kvsinyuk.stickergenerator.applicaiton.service.CropImageService
import com.kvsinyuk.stickergenerator.applicaiton.utils.mapToBufferedImage
import com.kvsinyuk.stickergenerator.domain.ImageData
import org.apache.commons.io.FilenameUtils
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CropImageServiceTest {

	@Autowired
	private lateinit var cropImageService: CropImageService

	private val examplePath = "/images/carImage.png"

	@Test
	fun contextLoads() {
		// given
		val imageUrl = javaClass.getResource(examplePath)
		val image = ImageData(imageUrl.readBytes(), imageUrl.file, FilenameUtils.getExtension(imageUrl.file))

		// when
		val croppedImage = cropImageService.cropImage(image)
			.mapToBufferedImage()

		// then
		assert(croppedImage.height == 228)
		assert(croppedImage.width == 427)
	}

}
