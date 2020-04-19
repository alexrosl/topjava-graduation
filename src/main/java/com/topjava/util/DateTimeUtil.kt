package com.topjava.util

import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object DateTimeUtil {
    val DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    fun toString(ldt: LocalDateTime?): String = if (ldt == null) "" else ldt.format(DATE_TIME_FORMATTER)

    fun createDateTime(date: LocalDate?, defaultDate: LocalDate, time: LocalTime): LocalDateTime? {
        return LocalDateTime.of( date ?: defaultDate, time)
    }
}