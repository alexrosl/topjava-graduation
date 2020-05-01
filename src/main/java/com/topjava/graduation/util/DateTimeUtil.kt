package com.topjava.graduation.util

import org.springframework.util.StringUtils
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

object DateTimeUtil {
    private val DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")!!
    private val MIN_DATE = LocalDateTime.of(1, 1, 1, 0, 0)
    private val MAX_DATE = LocalDateTime.of(3000, 1, 1, 0, 0)

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

    fun getStartInclusive(localDate: LocalDate?): LocalDateTime {
        return localDate?.atStartOfDay() ?: MIN_DATE
    }

    fun getEndExclusive(localDate: LocalDate?): LocalDateTime {
        return if (localDate != null) {
            localDate.plus(1, ChronoUnit.DAYS).atStartOfDay()
        } else {
            MAX_DATE
        }
    }
}