package com.kvsinyuk.stickergenerator

import com.kvsinyuk.stickergenerator.applicaiton.service.PadImageService
import com.kvsinyuk.stickergenerator.applicaiton.utils.toBufferedImage
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles

@ActiveProfiles("test")
@SpringBootTest
class PadImageServiceTest {
    @Autowired
    private lateinit var padImageService: PadImageService

    private val examplePath = "/images/carImage.png"

    @Test
    fun `should add padding to image when has top text`() {
        // given
        val resourceImage =
            javaClass.getResource(examplePath).readBytes()
                .toBufferedImage()

        // when
        val paddedImage = padImageService.addPaddingIfNecessary(resourceImage, true)

        // then
        assert(paddedImage.width == paddedImage.width)
        assert(resourceImage.height < paddedImage.height)
    }

    @Test
    fun `should not add padding to image with no top text`() {
        // given
        val resourceImage =
            javaClass.getResource(examplePath).readBytes()
                .toBufferedImage()

        // when
        val paddedImage = padImageService.addPaddingIfNecessary(resourceImage, false)

        // then
        assert(paddedImage.width == paddedImage.width)
        assert(resourceImage.height == paddedImage.height)
    }
}
