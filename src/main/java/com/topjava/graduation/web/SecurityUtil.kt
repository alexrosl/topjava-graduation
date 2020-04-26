package com.topjava.graduation.web

import com.topjava.graduation.model.AbstractBaseEntity
import com.topjava.graduation.util.MealsUtil

object SecurityUtil {
    fun authUserId() = AbstractBaseEntity.START_SEQ
    fun authUserCaloriesPerDay() = MealsUtil.DEFAULT_CALORIES_PER_DAY
}