package com.kvsinyuk.stickergenerator.applicaiton.impl

import com.kvsinyuk.stickergenerator.applicaiton.port.MessageSourcePort
import org.springframework.context.MessageSource
import org.springframework.stereotype.Component
import java.util.Locale

@Component
class MessageSourcePortImpl(
    private val messageSource: MessageSource,
) : MessageSourcePort {
    override fun getMessage(code: String): String {
        return messageSource.getMessage(code, null, Locale.ENGLISH)
    }
}
