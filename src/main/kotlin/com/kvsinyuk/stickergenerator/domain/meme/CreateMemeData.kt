package com.kvsinyuk.stickergenerator.domain.meme

import com.kvsinyuk.stickergenerator.domain.CommandData
import org.springframework.data.annotation.TypeAlias

@TypeAlias("create_meme")
data class CreateMemeData(
    var status: MemeStatus = MemeStatus.INIT,
    var originalFilename: String = "result.png",
    var topText: String = "",
    var bottomText: String = "",
) : CommandData() {
    var image: ByteArray? = null

    override fun isMemeData() = true
}
