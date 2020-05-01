package com.topjava.graduation.to

import java.time.LocalDateTime

data class MealTo(
        val id: Int? = null,
        val dateTime: LocalDateTime? = null,
        val description: String? = null,
        val calories: Int? = null,
        val excess: Boolean? = null
)