package com.topjava.graduation.web

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.MessageSourceResolvable
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component
import java.util.*

@Component
class MessageUtil {
    companion object {
        val RU_LOCALE = Locale("ru")
    }

    private lateinit var messageSource: MessageSource

    @Autowired
    constructor(messageSource: MessageSource) {
        this.messageSource = messageSource
    }

    fun getMessage(code: String, locale: Locale): String {
        return messageSource.getMessage(code, null, locale)
    }

    fun getMessage(code: String): String {
        return getMessage(code, LocaleContextHolder.getLocale())
    }

    fun getMessage(resolvable: MessageSourceResolvable): String {
        return messageSource.getMessage(resolvable, LocaleContextHolder.getLocale())
    }

}