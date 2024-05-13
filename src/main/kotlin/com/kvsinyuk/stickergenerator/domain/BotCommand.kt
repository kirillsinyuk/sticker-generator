package com.kvsinyuk.stickergenerator.domain

enum class BotCommand(
    val command: String,
    val description: String? = null,
) {
    START("/start"),
    HELP("/help"),
    CREATE_MEME("/crt_meme", "Create meme"),
    MAKE_STICKER("/crt_sticker", "Create telegram sticker"),
    FACE_SWAP("/swp_face", "Swap face on a picture"),
    REMOVE_BACKGROUND("/rm_bg", "Remove background from picture"),
}

val MENU_COMMANDS =
    setOf(
        BotCommand.MAKE_STICKER,
        BotCommand.CREATE_MEME,
        BotCommand.FACE_SWAP,
        BotCommand.REMOVE_BACKGROUND,
    )
