package com.topjava.graduation.util

import com.topjava.graduation.model.User
import com.topjava.graduation.to.UserTo

object UserUtil {
    const val DEFAULT_CALORIES_PER_DAY = 2000

    fun asTo(user: User): UserTo {
        return UserTo(user.id, user.name, user.email, user.password, user.caloriesPerDay)
    }

    fun updateFromTo(user: User, userTo: UserTo): User {
        user.name = userTo.name
        user.email = userTo.email?.toLowerCase()
        user.caloriesPerDay = user.caloriesPerDay
        user.password = user.password
        return user
    }
}