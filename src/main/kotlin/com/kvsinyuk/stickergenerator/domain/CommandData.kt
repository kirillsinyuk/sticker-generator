package com.kvsinyuk.stickergenerator.domain

abstract class CommandData {
    open fun isStickerData() = false

    open fun isFaceSwapData() = false

    open fun isRemoveBackgroundData() = false
}
