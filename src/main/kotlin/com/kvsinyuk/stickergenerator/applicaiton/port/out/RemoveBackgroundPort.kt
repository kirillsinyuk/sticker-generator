package com.kvsinyuk.stickergenerator.applicaiton.port.out

import com.kvsinyuk.stickergenerator.domain.StickerData

interface RemoveBackgroundPort {

    fun removeBackground(stickerData: StickerData): StickerData
}
