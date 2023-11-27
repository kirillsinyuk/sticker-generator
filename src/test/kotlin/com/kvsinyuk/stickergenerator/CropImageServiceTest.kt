package com.kvsinyuk.stickergenerator

import com.kvsinyuk.stickergenerator.service.CropImageService
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import java.util.*
import javax.imageio.ImageIO

@SpringBootTest
class CropImageServiceTest(
	val cropImageService: CropImageService
) {

	private val examplePath = "images/carImage.png"

	@Test
	fun contextLoads() {
		val image = ImageIO.read(Objects.requireNonNull(javaClass.getResource(examplePath)))
		val croppedImage = cropImageService.cropImage(image)
	}

}
