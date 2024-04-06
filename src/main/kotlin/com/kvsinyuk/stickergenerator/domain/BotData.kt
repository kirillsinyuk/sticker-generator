package com.kvsinyuk.stickergenerator.domain

import com.kvsinyuk.stickergenerator.domain.faceswap.FaceSwapData
import com.kvsinyuk.stickergenerator.domain.faceswap.FaceSwapStatus
import com.kvsinyuk.stickergenerator.domain.sticker.CreateStickerData
import com.kvsinyuk.stickergenerator.domain.sticker.StickerStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class BotData(
    @Id
    val chatId: Long,
    var commandData: CommandData,
) {
    fun getAsCreateStickerData() = commandData as CreateStickerData

    fun getAsFaceSwapData() = commandData as FaceSwapData

    fun isRemoveBackgroundData() = commandData.isRemoveBackgroundData()

    fun isStickerData() = commandData.isStickerData()

    fun isFaceSwapData() = commandData.isFaceSwapData()

    fun isStickerDataWithTopText() = isStickerData() && getAsCreateStickerData().status == StickerStatus.TOP_TEXT_ADDED

    fun isStickerDataWithSourceFile() = isStickerData() && getAsCreateStickerData().status == StickerStatus.SOURCE_FILE_ADDED

    fun isFaceSwapDataWithSourceFile() = isFaceSwapData() && getAsFaceSwapData().status == FaceSwapStatus.SOURCE_FILE_ADDED

    fun isFaceSwapDataInit() = isFaceSwapData() && getAsFaceSwapData().status == FaceSwapStatus.INIT
}
