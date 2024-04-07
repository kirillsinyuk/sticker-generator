package com.kvsinyuk.stickergenerator.domain

enum class BotCommand(
    val command: String,
    val description: String? = null,
) {
    START("/start"),
    HELP("/help"),
    CREATE_MEME("/mkmeme", "Create meme"),
    MAKE_STICKER("/mksticker", "Create telegram sticker"),
    FACE_SWAP("/swapface", "Swap face on a picture"),
    REMOVE_BACKGROUND("/rmbackground", "Remove background from picture"),
}

val MENU_COMMANDS =
    setOf(
        BotCommand.MAKE_STICKER,
        BotCommand.CREATE_MEME,
        BotCommand.FACE_SWAP,
        BotCommand.REMOVE_BACKGROUND,
    )
