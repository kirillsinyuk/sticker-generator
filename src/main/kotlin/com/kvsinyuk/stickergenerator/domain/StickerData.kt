package com.kvsinyuk.stickergenerator.domain

data class StickerData(
    val image: ByteArray,
    val originalFilename: String,
    val topText: String,
    val bottomText: String
)