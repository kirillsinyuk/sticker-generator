package com.kvsinyuk.stickergenerator.applicaiton.port

import com.kvsinyuk.stickergenerator.domain.StickerData

interface CropImageService {

    fun cropImage(stickerData: StickerData): StickerData
}