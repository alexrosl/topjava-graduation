package com.topjava.graduation.util

import com.topjava.graduation.model.Role
import com.topjava.graduation.model.User
import com.topjava.graduation.to.UserTo
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.util.StringUtils

object UserUtil {
    const val DEFAULT_CALORIES_PER_DAY = 2000

    fun createNewFromTo(userTo: UserTo): User {
        return User(null,
                userTo.name,
                userTo.email!!.toLowerCase(),
                userTo.password,
                userTo.caloriesPerDay,
                Role.ROLE_USER)
    }

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

    fun prepareToSave(user: User, passwordEncoder: PasswordEncoder): User {
        val password = user.password!!
        user.password = if (StringUtils.hasText(password)) {
            passwordEncoder.encode(password)
        } else {
            password
        }
        user.email = user.email!!.toLowerCase()
        return user
    }
}