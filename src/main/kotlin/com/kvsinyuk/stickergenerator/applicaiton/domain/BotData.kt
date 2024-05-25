package com.kvsinyuk.stickergenerator.applicaiton.domain

import com.kvsinyuk.stickergenerator.applicaiton.domain.command.CommandData
import com.kvsinyuk.stickergenerator.applicaiton.domain.command.FaceSwapData
import com.kvsinyuk.stickergenerator.applicaiton.domain.command.FaceSwapStatus
import com.kvsinyuk.stickergenerator.applicaiton.domain.command.MemeData
import com.kvsinyuk.stickergenerator.applicaiton.domain.command.MemeStatus
import com.kvsinyuk.stickergenerator.applicaiton.domain.command.StickerData
import com.kvsinyuk.stickergenerator.applicaiton.domain.command.StickerStatus
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

    fun addImage(image: Image): BotData {
        when (commandData) {
            is MemeData -> commandData.addImage(image)
            is StickerData -> commandData.addImage(image)
            is FaceSwapData -> commandData.addImage(image)
            else -> {}
        }
        return this
    }

    fun getAsStickerData() = commandData as StickerData

    fun getAsMemeData() = commandData as MemeData

    fun getAsFaceSwapData() = commandData as FaceSwapData

    fun isRemoveBackgroundData() = commandData.isRemoveBackgroundData()

    fun isStickerData() = commandData.isStickerData()

    fun isMemeData() = commandData.isMemeData()

    fun isFaceSwapData() = commandData.isFaceSwapData()

    fun isStickerDataWithTopText() = isStickerData() && getAsStickerData().status == StickerStatus.TOP_TEXT_ADDED

    fun isStickerDataWithSourceFile() = isStickerData() && getAsStickerData().status == StickerStatus.SOURCE_FILE_ADDED

    fun isMemeDataWithSourceFile() = isMemeData() && getAsMemeData().status == MemeStatus.SOURCE_FILE_ADDED

    fun isMemeDataWithTopText() = isMemeData() && getAsMemeData().status == MemeStatus.TOP_TEXT_ADDED

    fun isFaceSwapDataWithSourceFile() = isFaceSwapData() && getAsFaceSwapData().status == FaceSwapStatus.SOURCE_FILE_ADDED

    fun isFaceSwapDataInit() = isFaceSwapData() && getAsFaceSwapData().status == FaceSwapStatus.INIT
}
