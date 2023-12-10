package com.kvsinyuk.stickergenerator.applicaiton.port

import java.awt.image.BufferedImage

interface AddTextService {

    fun addText(image: BufferedImage, text: String, isTop: Boolean): BufferedImage
}