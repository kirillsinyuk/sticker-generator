package com.kvsinyuk.stickergenerator

import com.kvsinyuk.stickergenerator.applicaiton.service.CropImageService
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import javax.imageio.ImageIO

@SpringBootTest
class CropImageServiceTest {

	@Autowired
	private lateinit var cropImageService: CropImageService

	private val examplePath = "/images/carImage.png"

	@Test
	fun contextLoads() {
		val image = ImageIO.read(Objects.requireNonNull(javaClass.getResource(examplePath)))
		val croppedImage = cropImageService.cropImage(image)
		assert(croppedImage.height == 228)
		assert(croppedImage.width == 427)
	}

}
