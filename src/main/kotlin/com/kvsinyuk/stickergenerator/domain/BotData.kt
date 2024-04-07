package com.kvsinyuk.stickergenerator.domain

import com.kvsinyuk.stickergenerator.domain.faceswap.FaceSwapData
import com.kvsinyuk.stickergenerator.domain.faceswap.FaceSwapStatus
import com.kvsinyuk.stickergenerator.domain.meme.CreateMemeData
import com.kvsinyuk.stickergenerator.domain.sticker.CreateStickerData
import com.kvsinyuk.stickergenerator.domain.sticker.StickerStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class BotData(
    @Id
    val chatId: Long,
    val commandData: CommandData,
) {
    fun addText(
        text: String,
        isTop: Boolean = false,
    ): BotData {
        when (commandData) {
            is CreateMemeData -> commandData.addText(text, isTop)
            is CreateStickerData -> commandData.addText(text, isTop)
        }
        return this
    }

    fun addImage(
        file: ByteArray,
        fileName: String,
    ): BotData {
        when (commandData) {
            is CreateMemeData -> commandData.addImage(file, fileName)
            is CreateStickerData -> commandData.addImage(file, fileName)
            is FaceSwapData -> commandData.addImage(file, fileName)
        }
        return this
    }

    fun getAsCreateStickerData() = commandData as CreateStickerData

    fun getAsFaceSwapData() = commandData as FaceSwapData

    fun getAsCreateMemeData() = commandData as CreateMemeData

    fun isRemoveBackgroundData() = commandData.isRemoveBackgroundData()

    fun isStickerData() = commandData.isStickerData()

    fun isMemeData() = commandData.isMemeData()

    fun isFaceSwapData() = commandData.isFaceSwapData()

    fun isStickerDataWithTopText() = isStickerData() && getAsCreateStickerData().status == StickerStatus.TOP_TEXT_ADDED

    fun isStickerDataWithSourceFile() = isStickerData() && getAsCreateStickerData().status == StickerStatus.SOURCE_FILE_ADDED

    fun isFaceSwapDataWithSourceFile() = isFaceSwapData() && getAsFaceSwapData().status == FaceSwapStatus.SOURCE_FILE_ADDED

    fun isFaceSwapDataInit() = isFaceSwapData() && getAsFaceSwapData().status == FaceSwapStatus.INIT
}
