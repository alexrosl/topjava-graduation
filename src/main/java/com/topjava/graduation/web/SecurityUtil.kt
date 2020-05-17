package com.topjava.graduation.web

import com.topjava.graduation.AuthorizedUser
import com.topjava.graduation.model.AbstractBaseEntity
import com.topjava.graduation.util.MealsUtil
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

object SecurityUtil {
    fun authUserId() = AbstractBaseEntity.START_SEQ
    fun authUserCaloriesPerDay() = MealsUtil.DEFAULT_CALORIES_PER_DAY

    fun safeGet(): AuthorizedUser? {
        val auth = SecurityContextHolder.getContext().authentication ?: return null
        val principal = auth.principal
        return if (principal is AuthorizedUser) principal else null
    }

    fun get(): AuthorizedUser? {
        val user: AuthorizedUser? = safeGet()
        Objects.requireNonNull<Any>(user, "No authorized user found")
        return user
    }
}