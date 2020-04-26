package com.topjava.graduation.web.converter

import com.topjava.graduation.util.DateTimeUtil
import org.springframework.format.Formatter
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*

object LocalDateFormatter : Formatter<LocalDate> {
    override fun print(`object`: LocalDate, locale: Locale): String {
        return `object`.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    override fun parse(text: String, locale: Locale): LocalDate {
        return DateTimeUtil.parseLocalDate(text)!!
    }
}

object LocalTimeFormatter : Formatter<LocalTime> {
    override fun print(`object`: LocalTime, locale: Locale): String {
        return `object`.format(DateTimeFormatter.ISO_LOCAL_TIME)
    }

    override fun parse(text: String, locale: Locale): LocalTime {
        return DateTimeUtil.parseLocalTime(text)!!
    }

}