package com.kvsinyuk.stickergenerator.applicaiton.port.out.mongo

import com.kvsinyuk.stickergenerator.domain.StickerData

interface SaveStickerDataPort {

    fun save(stickerData: StickerData): StickerData
}