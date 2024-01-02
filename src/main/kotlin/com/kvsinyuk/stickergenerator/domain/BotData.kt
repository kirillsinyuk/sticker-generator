package com.kvsinyuk.stickergenerator.domain

import com.kvsinyuk.stickergenerator.domain.faceswap.FaceSwapData
import com.kvsinyuk.stickergenerator.domain.sticker.CreateStickerData
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document
data class BotData(
    @Id
    val chatId: Long,
    var commandData: CommandData
) {

    fun getAsCreateStickerData() =
        commandData as CreateStickerData

    fun getAsFaceSwapData() =
        commandData as FaceSwapData
}