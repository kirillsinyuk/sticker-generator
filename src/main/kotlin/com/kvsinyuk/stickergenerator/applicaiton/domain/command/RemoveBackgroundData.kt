package com.kvsinyuk.stickergenerator.applicaiton.domain.command

import com.kvsinyuk.stickergenerator.applicaiton.domain.Image
import org.springframework.data.annotation.TypeAlias

@TypeAlias("remove_background")
data class RemoveBackgroundData(
    val status: RemoveBackgroundStatus = RemoveBackgroundStatus.INIT,
) : CommandData() {
    override fun isRemoveBackgroundData() = true

    override fun getSourceImage(): Image {
        TODO("Won't be implemented")
    }
}

enum class RemoveBackgroundStatus {
    INIT,
}
