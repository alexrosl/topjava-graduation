package com.topjava.model

import java.time.LocalDateTime

data class Meal (
        override var id: Int? = null,
        val dateTime: LocalDateTime,
        val description: String,
        val calories: Int
) : AbstractBaseEntity(id)

