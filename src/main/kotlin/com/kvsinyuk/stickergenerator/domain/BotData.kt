package com.kvsinyuk.stickergenerator.domain

import com.kvsinyuk.stickergenerator.domain.command.CommandData
import com.kvsinyuk.stickergenerator.domain.command.FaceSwapData
import com.kvsinyuk.stickergenerator.domain.command.FaceSwapStatus
import com.kvsinyuk.stickergenerator.domain.command.MemeData
import com.kvsinyuk.stickergenerator.domain.command.StickerData
import com.kvsinyuk.stickergenerator.domain.command.StickerStatus
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class BotData(
    @Id
    val chatId: Long,
    val commandData: CommandData,
) {
    fun getTopText(): String {
        return when (commandData) {
            is MemeData -> commandData.topText
            is StickerData -> commandData.topText
            else -> ""
        }
    }

    fun setImage(image: Image): BotData {
        when (commandData) {
            is MemeData -> commandData.image = image
            is StickerData -> commandData.image = image
            else -> {}
        }
        return this
    }

    fun addText(
        text: String,
        isTop: Boolean = false,
    ): BotData {
        when (commandData) {
            is MemeData -> commandData.addText(text, isTop)
            is StickerData -> commandData.addText(text, isTop)
            else -> {}
        }
        return this
    }

    fun addImage(
        file: ByteArray,
        fileName: String,
    ): BotData {
        when (commandData) {
            is MemeData -> commandData.addImage(file, fileName)
            is StickerData -> commandData.addImage(file, fileName)
            is FaceSwapData -> commandData.addImage(file, fileName)
            else -> {}
        }
        return this
    }

    fun getAsStickerData() = commandData as StickerData

    fun getAsFaceSwapData() = commandData as FaceSwapData

    fun isRemoveBackgroundData() = commandData.isRemoveBackgroundData()

    fun isStickerData() = commandData.isStickerData()

    fun isMemeData() = commandData.isMemeData()

    fun isFaceSwapData() = commandData.isFaceSwapData()

    fun isStickerDataWithTopText() = isStickerData() && getAsStickerData().status == StickerStatus.TOP_TEXT_ADDED

    fun isStickerDataWithSourceFile() = isStickerData() && getAsStickerData().status == StickerStatus.SOURCE_FILE_ADDED

    fun isFaceSwapDataWithSourceFile() = isFaceSwapData() && getAsFaceSwapData().status == FaceSwapStatus.SOURCE_FILE_ADDED

    fun isFaceSwapDataInit() = isFaceSwapData() && getAsFaceSwapData().status == FaceSwapStatus.INIT
}
