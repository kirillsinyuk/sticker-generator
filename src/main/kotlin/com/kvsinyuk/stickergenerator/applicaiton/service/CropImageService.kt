package com.kvsinyuk.stickergenerator.applicaiton.service

import com.kvsinyuk.stickergenerator.domain.StickerData

interface CropImageService {

    fun cropImage(stickerData: StickerData): StickerData
}