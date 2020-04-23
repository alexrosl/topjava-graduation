package com.topjava.util

import org.springframework.util.StringUtils
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object DateTimeUtil {
    private val DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")!!
    private val MIN_DATE = LocalDate.of(1, 1, 1)
    private val MAX_DATE = LocalDate.of(3000, 1, 1)

    fun toString(ldt: LocalDateTime?): String = if (ldt == null) "" else ldt.format(DATE_TIME_FORMATTER)

    fun parseLocalDate(str: String?): LocalDate? {
        return if (StringUtils.isEmpty(str)) {
            null
        }  else {
            LocalDate.parse(str)
        }
    }

    fun parseLocalTime(str: String?): LocalTime? {
        return if (StringUtils.isEmpty(str)) {
            null
        } else {
            LocalTime.parse(str)
        }
    }

    fun getStartInclusive(localDate: LocalDate?): LocalDateTime? {
        return startOfDay(localDate ?: MIN_DATE)
    }

    fun getEndInclusive(localDate: LocalDate?): LocalDateTime {
        return startOfDay(if (localDate != null) localDate.plus(1, ChronoUnit.DAYS) else MAX_DATE)!!
    }

    fun createDateTime(date: LocalDate?, defaultDate: LocalDate, time: LocalTime): LocalDateTime? {
        return LocalDateTime.of( date ?: defaultDate, time)
    }

    fun startOfDay(localDate: LocalDate): LocalDateTime? {
        return LocalDateTime.of(localDate, LocalTime.MIN)
    }
}