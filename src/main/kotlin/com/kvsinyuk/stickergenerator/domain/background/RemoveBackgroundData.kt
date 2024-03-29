package com.kvsinyuk.stickergenerator.domain.background

import com.kvsinyuk.stickergenerator.domain.CommandData
import org.springframework.data.annotation.TypeAlias

@TypeAlias("remove_background")
data class RemoveBackgroundData(
    val status: RemoveBackgroundStatus = RemoveBackgroundStatus.INIT,
) : CommandData() {
    override fun isRemoveBackgroundData() = true
}
