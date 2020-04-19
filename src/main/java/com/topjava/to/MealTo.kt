package com.topjava.to

import java.time.LocalDateTime

data class MealTo(
        val int: Int?,
        val dateTime: LocalDateTime,
        val description: String,
        val calories: Int,
        val excess: Boolean
)