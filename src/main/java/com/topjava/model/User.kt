package com.topjava.model

import com.topjava.util.MealsUtil
import java.util.*

data class User(
        override var id: Int?,
        override val name: String,
        val email: String,
        val password: String,
        val enabled: Boolean = true,
        val caloriesPerDay: Int = MealsUtil.DEFAULT_CALORIES_PER_DAY,
        val registered: Date = Date(),
        val roles: Set<Role>
) : AbstractNamedEntity(id, name)