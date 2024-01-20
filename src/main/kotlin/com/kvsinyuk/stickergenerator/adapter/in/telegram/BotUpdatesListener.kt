package com.kvsinyuk.stickergenerator.adapter.`in`.telegram

import com.kvsinyuk.stickergenerator.adapter.`in`.telegram.handlers.TelegramUpdateHandler
import com.kvsinyuk.stickergenerator.adapter.mapper.TelegramUpdateMessageMapper
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.UpdatesListener
import com.pengrad.telegrambot.model.Update
import jakarta.annotation.PostConstruct
import mu.KLogging
import org.springframework.stereotype.Component

@Component
class BotUpdatesListener(
    private val bot: TelegramBot,
    private val telegramUpdateHandlers: List<TelegramUpdateHandler>,
    private val telegramUpdateMessageMapper: TelegramUpdateMessageMapper,
) : UpdatesListener {
    @PostConstruct
    fun init() {
        bot.setUpdatesListener(this)
    }

    override fun process(updates: MutableList<Update>?): Int {
        updates?.forEach { update ->
            val updateData = telegramUpdateMessageMapper.toMessage(update)
            logger.debug("Processing update $updateData")
            telegramUpdateHandlers.filter { it.canApply(updateData) }
                .forEach { it.process(updateData) }
        }
        return UpdatesListener.CONFIRMED_UPDATES_ALL
    }

    companion object : KLogging()
}
